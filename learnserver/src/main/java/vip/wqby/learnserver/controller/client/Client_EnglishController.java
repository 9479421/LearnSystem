package vip.wqby.learnserver.controller.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.wqby.learnserver.mapper.auto.EnglishMapper;
import vip.wqby.learnserver.model.auto.English;
import vip.wqby.learnserver.pojo.CommonResult;
import vip.wqby.learnserver.utils.TenCloudUtils;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2022-11-07
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/client/english")
public class Client_EnglishController {
    @Resource
    EnglishMapper englishMapper;
    @Autowired
    TenCloudUtils tenCloudUtils;
    @GetMapping("/getRandomVideo")
    public CommonResult getRandomVideo(){
        English randomVideo = englishMapper.getRandomVideo();
        HashMap hashMap = new HashMap();
        hashMap.put("url",tenCloudUtils.baseUrl + randomVideo.getPath());
        return CommonResult.ok("ok",hashMap);
    }
}
