package vip.wqby.learnserver.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.wqby.learnserver.pojo.CommonResult;
import vip.wqby.learnserver.service.ICourseService;

import javax.websocket.server.PathParam;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/client/course")
public class Client_CourseController {
    @Autowired
    private ICourseService iCourseService;

    @GetMapping("/listCourse")
    public CommonResult listCourse(@PathParam("page")int page, @RequestHeader("token")String token){
        return iCourseService.listCourse(token);
    }

    @GetMapping("/addCourse")
    public CommonResult addCourse( @RequestHeader("token")String token,@PathParam("courseId")String courseId){
        return iCourseService.addCourse(token,courseId);
    }

}
