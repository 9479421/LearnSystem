package vip.wqby.learnserver.controller.client;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import vip.wqby.learnserver.mapper.auto.CommentMapper;
import vip.wqby.learnserver.model.auto.Comment;
import vip.wqby.learnserver.pojo.CommonResult;
import vip.wqby.learnserver.service.ICommentService;
import vip.wqby.learnserver.utils.JWTUtil;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2022-11-04
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/client/comment")
public class Client_CommentController {

    @Autowired
    ICommentService iCommentService;
    @PostMapping("/addComment")
    public CommonResult addComment(@RequestHeader("token")String token, @RequestBody String requestBody){
        return iCommentService.addComment(token,requestBody);

    }
    @GetMapping("/getCommentList")
    public CommonResult getCommentList(@RequestHeader("token")String token, @PathParam("courseId") String courseId){
        return iCommentService.getCommentList(token,courseId);
    }
}
