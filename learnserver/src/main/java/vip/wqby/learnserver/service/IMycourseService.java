package vip.wqby.learnserver.service;

import vip.wqby.learnserver.model.auto.Mycourse;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.wqby.learnserver.pojo.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-10-20
 */
public interface IMycourseService extends IService<Mycourse> {
    public CommonResult addMyCourse(String token,String courseId);
    public CommonResult getMyCourseList(String token);
    public CommonResult getMyChapterList(String courseId,String token);


}
