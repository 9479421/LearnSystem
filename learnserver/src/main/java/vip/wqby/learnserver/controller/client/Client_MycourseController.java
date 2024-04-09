package vip.wqby.learnserver.controller.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import vip.wqby.learnserver.pojo.CommonResult;
import vip.wqby.learnserver.service.IMycourseService;

import javax.websocket.server.PathParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2022-10-20
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/client/mycourse")
public class Client_MycourseController {
    @Autowired
    private IMycourseService iMycourseService;

    @GetMapping("/addMyCourse")
    public CommonResult addMyCourse(@PathParam("memberId")String memberId, @RequestHeader("token")String token){
        return null;
    }

    @GetMapping("/getMyCourseList")
    public CommonResult getMyCourseList(@RequestHeader("token")String token){
        return iMycourseService.getMyCourseList(token);
    }

    @GetMapping("/getMyChapterList")
    public CommonResult getMyChapterList(@PathParam("courseId")String courseId,@RequestHeader("token")String token){
        return iMycourseService.getMyChapterList(courseId,token);
    }
}
