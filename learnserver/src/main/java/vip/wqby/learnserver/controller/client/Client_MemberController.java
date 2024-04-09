package vip.wqby.learnserver.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.wqby.learnserver.pojo.CommonResult;
import vip.wqby.learnserver.service.ICourseService;
import vip.wqby.learnserver.service.IMemberService;

import javax.websocket.server.PathParam;

@CrossOrigin("*")
@RestController
@RequestMapping("/client/member")
public class Client_MemberController {
    @Autowired
    private IMemberService iMemberService;

    @RequestMapping("/login")
    public CommonResult login(@RequestBody String requestBody){
        return iMemberService.login(requestBody);
    }
    @RequestMapping("/sendRegisterCode")
    public CommonResult sendRegisterCode(@PathParam("phone")String phone){
        return iMemberService.sendRegisterCode(phone);
    }
    @RequestMapping("/register")
    public CommonResult register(@RequestBody String requestBody){
        return iMemberService.register(requestBody);
    }

    @RequestMapping("/uploadAvatar")
    public CommonResult uploadAvatar(@RequestParam("image")MultipartFile image,@RequestHeader("token")String token){
        return iMemberService.uploadAvatar(image,token);
    }

    @RequestMapping("/modifyInfo")
    public CommonResult modifyInfo(@RequestBody String requestBody,@RequestHeader("token")String token){
        return iMemberService.modifyInfo(requestBody,token);
    }

    @RequestMapping("/modifyPass")
    public CommonResult modifyPass(@RequestBody String requestBody,@RequestHeader("token")String token){
        return iMemberService.modifyPass(requestBody,token);
    }
}
