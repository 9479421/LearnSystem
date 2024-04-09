package vip.wqby.learnserver.service;

import vip.wqby.learnserver.model.auto.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.wqby.learnserver.pojo.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-09-12
 */
public interface ICourseService extends IService<Course> {

    public CommonResult getCourseList(String searchType,String searchText);
    public CommonResult addCourse(String requestBody);
    public CommonResult changeCourseInfo(String requestBody);
    public CommonResult changePutAwayStatus(String courseId,String status);
    public CommonResult deleteCourse(String courseId);


    //client
    public CommonResult listCourse(String token);
    public CommonResult addCourse(String token,String courseId);
}
