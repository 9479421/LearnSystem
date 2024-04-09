package vip.wqby.learnserver.service;

import org.springframework.web.multipart.MultipartFile;
import vip.wqby.learnserver.model.auto.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.wqby.learnserver.pojo.CommonResult;

import java.sql.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-09-17
 */
public interface IChapterService extends IService<Chapter> {
    public CommonResult getChapterList(int courseId);
    public CommonResult addChapter(MultipartFile[] files,int courseId);
    public CommonResult deleteChapter(int id,String keyPath,String courseId);
    public CommonResult modifyChapter(int id,int courseId,MultipartFile file);
    public CommonResult saveChapterName(int id,String chapterName,String chapterId);
}
