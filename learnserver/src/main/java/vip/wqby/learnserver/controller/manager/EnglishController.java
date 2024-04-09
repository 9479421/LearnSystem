package vip.wqby.learnserver.controller.manager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import vip.wqby.learnserver.pojo.CommonResult;
import vip.wqby.learnserver.service.IEnglishService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2022-11-07
 */
@RestController
@RequestMapping("/manager/")
public class EnglishController {
    @Autowired
    IEnglishService iEnglishService;
    @PostMapping("/addVideo")
    public CommonResult addVideo(@RequestParam("file") MultipartFile[] files){
        return iEnglishService.addVideo(files);
    }
    @GetMapping("/listVideos")
    public CommonResult listVideos(){
        return iEnglishService.listVideos();
    }
}
