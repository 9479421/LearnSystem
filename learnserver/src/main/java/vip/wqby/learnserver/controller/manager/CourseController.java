package vip.wqby.learnserver.controller.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.wqby.learnserver.pojo.CommonResult;
import vip.wqby.learnserver.service.ICourseService;
import vip.wqby.learnserver.utils.TenCloudUtils;
import vip.wqby.learnserver.utils.logUtils.qLog;

import javax.websocket.server.PathParam;
import java.util.HashMap;

@RestController
@RequestMapping("/manager")
public class CourseController {
    @Autowired
    private ICourseService iCourseService;

    @GetMapping("/getCourseList")
    public CommonResult getCourseList(@PathParam("searchType")String searchType,@PathParam("searchText")String searchText){
        return iCourseService.getCourseList(searchType,searchText);
    }
    @PostMapping("/addCourse")
    public CommonResult addCourse(@RequestBody String requestBody){
        return iCourseService.addCourse(requestBody);
    }
    @PostMapping("/changeCourseInfo")
    public CommonResult changeCourseInfo(@RequestBody String requestBody){
        return iCourseService.changeCourseInfo(requestBody);
    }
    @GetMapping("/changePutAwayStatus")
    public CommonResult changePutAwayStatus(@PathParam("courseId")String courseId, @PathParam("status")String status){
        qLog.info(courseId,status);
        return iCourseService.changePutAwayStatus(courseId,status);
    }
    @GetMapping("/deleteCourse")
    public CommonResult deleteCourse(@PathParam("courseId")String courseId){
        return iCourseService.deleteCourse(courseId);
    }



}
