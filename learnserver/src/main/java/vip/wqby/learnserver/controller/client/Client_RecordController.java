package vip.wqby.learnserver.controller.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import vip.wqby.learnserver.pojo.CommonResult;
import vip.wqby.learnserver.service.IRecordService;

import javax.websocket.server.PathParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2022-11-02
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/client/record")
public class Client_RecordController {
    @Autowired
    private IRecordService iRecordService;
    @RequestMapping("/updateRecord")
    public CommonResult updateRecord( @RequestHeader("token")String token,@PathParam("courseId")String courseId,@PathParam("chapterId")String chapterId,@PathParam("currentTime")String currentTime,@PathParam("allTime")String allTime){
        return iRecordService.updateRecord(token,courseId,chapterId,currentTime, allTime);
    }
}
