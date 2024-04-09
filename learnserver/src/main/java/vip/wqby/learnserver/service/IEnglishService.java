package vip.wqby.learnserver.service;

import org.springframework.web.multipart.MultipartFile;
import vip.wqby.learnserver.model.auto.English;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.wqby.learnserver.pojo.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-11-07
 */
public interface IEnglishService extends IService<English> {
    public CommonResult addVideo(MultipartFile[] files);
    public CommonResult listVideos();
}
