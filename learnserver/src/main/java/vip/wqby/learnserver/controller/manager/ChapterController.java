package vip.wqby.learnserver.controller.manager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.wqby.learnserver.pojo.CommonResult;
import vip.wqby.learnserver.service.IChapterService;
import vip.wqby.learnserver.utils.TenCloudUtils;
import vip.wqby.learnserver.utils.logUtils.qLog;

import javax.websocket.server.PathParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2022-09-17
 */
@RestController
@RequestMapping("/manager")
public class ChapterController {
    @Autowired
    TenCloudUtils tenCloudUtils;
    @Autowired
    IChapterService iChapterService;
    @PostMapping("/addChapter")
    public CommonResult addChapter(@RequestParam("file")MultipartFile[] files,@PathParam("courseId")int courseId){
        return iChapterService.addChapter(files,courseId);
    }

    @GetMapping("/getChapterList")
    public CommonResult getChapterList(@PathParam("courseId")int courseId){
        return iChapterService.getChapterList(courseId);
    }

    @GetMapping("/deleteChapter")
    public CommonResult deleteChapter(@PathParam("id")int id,@PathParam("keyPath")String keyPath,@PathParam("courseId")String courseId){
        return iChapterService.deleteChapter(id,keyPath,courseId);
    }

    @PostMapping("/modifyChapter")
    public CommonResult modifyChapter(@PathParam("id")int id,@PathParam("courseId")int courseId,@RequestParam("file")MultipartFile file){
        return iChapterService.modifyChapter(id,courseId,file);
    }

    @PostMapping("/saveChapterName")
    public CommonResult saveChapterName(@PathParam("id")int id,@PathParam("newName")String newName,@PathParam("chapterId")String chapterId){
        return iChapterService.saveChapterName(id,newName,chapterId);
    }


}
