package vip.wqby.learnserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import vip.wqby.learnserver.model.auto.Chapter;
import vip.wqby.learnserver.mapper.auto.ChapterMapper;
import vip.wqby.learnserver.pojo.CommonResult;
import vip.wqby.learnserver.pojo.UploadVideo;
import vip.wqby.learnserver.service.IChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.wqby.learnserver.utils.TenCloudUtils;
import vip.wqby.learnserver.utils.logUtils.qLog;

import javax.annotation.Resource;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-09-17
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements IChapterService {
    @Resource
    ChapterMapper chapterMapper;
    @Autowired
    TenCloudUtils tenCloudUtils;

    @Override
    public CommonResult getChapterList(int courseId_) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("courseId", courseId_);
        List<Chapter> list = chapterMapper.selectList(qw);
        List retList = new ArrayList();
        for (int i = 0 ; i < list.size();i++){
            HashMap hashMap = new HashMap<>();
            String path = list.get(i).getPath();
            Integer id = list.get(i).getId();
            Integer courseId = list.get(i).getCourseId();
            Integer chapterId = list.get(i).getChapterId();
            String chapterName = list.get(i).getChapterName();
            String digest = list.get(i).getDigest();
            String duration = list.get(i).getDuration();
            hashMap.put("id",id);
            hashMap.put("path",path);
            hashMap.put("realPath",tenCloudUtils.baseUrl+path);
            hashMap.put("courseId",courseId);
            hashMap.put("chapterId",chapterId);
            hashMap.put("chapterName",chapterName);
            hashMap.put("digest",digest);
            hashMap.put("duration",duration);
            retList.add(hashMap);
        }

        return new CommonResult(200, "查询成功",retList );
    }

    @Override
    public CommonResult addChapter(MultipartFile[] files, int courseId) {
        List<UploadVideo> uploadVideos = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                int endIndex = file.getOriginalFilename().lastIndexOf(".mp4");
                String chapterName = file.getOriginalFilename().substring(0, endIndex);
                String md5Text = file.getOriginalFilename() + System.currentTimeMillis() + Math.random() * 10;
                String md5Digest = DigestUtils.md5DigestAsHex(md5Text.getBytes());
                String uploadFileName = md5Digest + ".mp4";
                String keyPath = "/" + courseId + "/" + uploadFileName;
                //添加到临时数组
                uploadVideos.add(new UploadVideo(keyPath, md5Digest));
                qLog.info("开始上传");
                tenCloudUtils.uploadFile(file.getInputStream(), keyPath);
                qLog.info("结束上传");
                int chapterCounts = chapterMapper.getChapterCounts(courseId);
                String duration = tenCloudUtils.getVideoDuration(keyPath);
                Chapter chapter = new Chapter(null, chapterCounts + 1, md5Digest, keyPath, courseId, chapterName, duration);
                chapterMapper.addChapter(chapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //删除云视频和数据
            for (UploadVideo uv : uploadVideos) {
                try {
                    QueryWrapper qw = new QueryWrapper();
                    qw.eq("digest", uv.getDigest());
                    chapterMapper.delete(qw);
                    tenCloudUtils.deleteFile(uv.getUploadKeyPath());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return CommonResult.failed(e.getMessage());
                }
            }

            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.ok("上传章节成功");
    }

    @Override
    public CommonResult deleteChapter(int id, String keyPath, String courseId) {
        try {
            tenCloudUtils.deleteFile(keyPath);
            chapterMapper.deleteById(id);
            //操作数据库
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("courseId", courseId);
            queryWrapper.orderByAsc("chapterId");
            List<Chapter> list = chapterMapper.selectList(queryWrapper);
            for (int i = 0; i < list.size(); i++) {
                UpdateWrapper updateWrapper = new UpdateWrapper();
                updateWrapper.eq("id", list.get(i).getId());
                updateWrapper.set("chapterId", i + 1);
                chapterMapper.update(null, updateWrapper);
            }
        } catch (Exception e) {
            return CommonResult.failed("删除失败！");
        }
        return CommonResult.ok("删除章节成功");
    }

    //暂时先不写
    @Override
    public CommonResult modifyChapter(int id, int courseId, MultipartFile file) {
        try {
            int endIndex = file.getOriginalFilename().lastIndexOf(".mp4");
            String chapterName = file.getOriginalFilename().substring(0, endIndex);
            String md5Text = file.getOriginalFilename() + System.currentTimeMillis() + Math.random() * 10;
            String md5Digest = DigestUtils.md5DigestAsHex(md5Text.getBytes());
            String uploadFileName = md5Digest + ".mp4";
            String keyPath = "/" + courseId + "/" + uploadFileName;
            //添加到临时数组
            tenCloudUtils.uploadFile(file.getInputStream(), keyPath);
            int chapterCounts = chapterMapper.getChapterCounts(courseId);
            String duration = tenCloudUtils.getVideoDuration(keyPath);
            Chapter chapter = new Chapter(null, chapterCounts + 1, md5Digest, keyPath, courseId, chapterName, duration);
            chapterMapper.addChapter(chapter);
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public CommonResult saveChapterName(int id, String chapterName,String chapterId) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", id);
        updateWrapper.set("chapterName", chapterName);
        updateWrapper.set("chapterId", chapterId);
        chapterMapper.update(null,updateWrapper );
        return CommonResult.ok("改名成功");
    }

}
