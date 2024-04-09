package vip.wqby.learnserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import vip.wqby.learnserver.model.auto.Chapter;
import vip.wqby.learnserver.model.auto.English;
import vip.wqby.learnserver.mapper.auto.EnglishMapper;
import vip.wqby.learnserver.pojo.CommonResult;
import vip.wqby.learnserver.pojo.UploadVideo;
import vip.wqby.learnserver.service.IEnglishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.wqby.learnserver.utils.TenCloudUtils;
import vip.wqby.learnserver.utils.logUtils.qLog;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-11-07
 */
@Service
public class EnglishServiceImpl extends ServiceImpl<EnglishMapper, English> implements IEnglishService {
    @Resource
    EnglishMapper englishMapper;
    @Autowired
    TenCloudUtils tenCloudUtils;

    @Override
    public CommonResult addVideo(MultipartFile[] files) {
        List<UploadVideo> uploadVideos = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                String md5Text = file.getOriginalFilename() + System.currentTimeMillis() + Math.random() * 10;
                String md5Digest = DigestUtils.md5DigestAsHex(md5Text.getBytes());
                String uploadFileName = md5Digest + ".mp4";
                String keyPath = "/english/" + uploadFileName;
                //添加到临时数组
                String digest = DigestUtils.md5DigestAsHex(file.getInputStream());  //digest用于后期检测视频相同使用 暂时用不到
                uploadVideos.add(new UploadVideo(keyPath, digest));
                qLog.info("开始上传");
                tenCloudUtils.uploadFile(file.getInputStream(), keyPath);
                qLog.info("结束上传");

                English english = new English(null, keyPath, digest);
                englishMapper.insert(english);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //删除云视频和数据
            for (UploadVideo uv : uploadVideos) {
                try {
                    QueryWrapper qw = new QueryWrapper();
                    qw.eq("digest", uv.getDigest());
                    englishMapper.delete(qw);
                    tenCloudUtils.deleteFile(uv.getUploadKeyPath());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return CommonResult.failed(e.getMessage());
                }
            }

            return CommonResult.failed(e.getMessage());
        }
        return CommonResult.ok("上传短视频成功");
    }

    @Override
    public CommonResult listVideos() {
        List<English> englishes = englishMapper.selectList(new QueryWrapper<>());
        List retList = new ArrayList();
        for (int i = 0; i < englishes.size(); i++) {
            HashMap hashMap = new HashMap();
            hashMap.put("id", englishes.get(i).getId());
            hashMap.put("path", englishes.get(i).getPath());
            hashMap.put("digest", englishes.get(i).getDigest());
            hashMap.put("realPath", tenCloudUtils.baseUrl + englishes.get(i).getPath());
            retList.add(hashMap);
        }
        return CommonResult.ok("查询成功", retList);
    }
}
