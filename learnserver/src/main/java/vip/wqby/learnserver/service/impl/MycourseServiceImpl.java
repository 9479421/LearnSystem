package vip.wqby.learnserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import vip.wqby.learnserver.mapper.auto.ChapterMapper;
import vip.wqby.learnserver.mapper.auto.RecordMapper;
import vip.wqby.learnserver.model.auto.*;
import vip.wqby.learnserver.mapper.auto.MycourseMapper;
import vip.wqby.learnserver.pojo.CommonResult;
import vip.wqby.learnserver.service.IMycourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.wqby.learnserver.utils.JWTUtil;
import vip.wqby.learnserver.utils.TenCloudUtils;
import vip.wqby.learnserver.utils.Utils;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-10-20
 */
@Service
public class MycourseServiceImpl extends ServiceImpl<MycourseMapper, Mycourse> implements IMycourseService {
    @Resource
    MycourseMapper myCourseMapper;
    @Resource
    RecordMapper recordMapper;
    @Resource
    ChapterMapper chapterMapper;
    @Autowired
    TenCloudUtils tenCloudUtils;

    @Override
    public CommonResult addMyCourse(String token, String courseId) {
        String memberId = JWTUtil.getMemberIdFromToken(token);
        int ret = myCourseMapper.insert(new Mycourse(null, courseId, memberId));
        if (ret == 1) {
            return CommonResult.ok("添加成功");
        }
        return CommonResult.failed("添加失败");
    }

    @Override
    public CommonResult getMyCourseList(String token) {
        String memberId = JWTUtil.getMemberIdFromToken(token);
        List<Course> myCourseList = myCourseMapper.getMyCourseList(memberId);

        List<Course_RET> retCourseList = new LinkedList<>();
        for (int i = 0; i < myCourseList.size(); i++) {
            Integer courseId = myCourseList.get(i).getCourseId();
            //获取章节数 和 record已完成的记录数
            QueryWrapper queryWrapper_chapterNum = new QueryWrapper();
            queryWrapper_chapterNum.eq("courseId",courseId);
            int chapterNum = chapterMapper.selectList(queryWrapper_chapterNum).size();

            QueryWrapper queryWrapper_recordNum = new QueryWrapper();
            queryWrapper_recordNum.eq("memberId",memberId);
            queryWrapper_recordNum.eq("courseId",courseId);
            queryWrapper_recordNum.eq("isWatched",true);
            int recordNum = recordMapper.selectList(queryWrapper_recordNum).size();
            int progress = Utils.toPercent(recordNum,chapterNum);
            retCourseList.add(new Course_RET(courseId,myCourseList.get(i).getName(),
                    myCourseList.get(i).getTeacher(),myCourseList.get(i).getCoverImg(),
                    myCourseList.get(i).getIntro(),myCourseList.get(i).getIsPutAway(),false,progress));
        }
        return CommonResult.ok("查询成功", retCourseList);
    }

    @Override
    public CommonResult getMyChapterList(String courseId, String token) {
        String memberId = JWTUtil.getMemberIdFromToken(token);
        List<Chapter> myChapterList = myCourseMapper.getMyChapterList(courseId);
        Iterator iterator = myChapterList.iterator();
        ArrayList chapterList = new ArrayList();
        while (iterator.hasNext()) {
            Chapter next = (Chapter) iterator.next();
            Map map = new HashMap();
            map.put("courseId", next.getCourseId());
            map.put("chapterId", next.getChapterId());
            map.put("chapterName", next.getChapterName());
            map.put("path", tenCloudUtils.baseUrl + next.getPath());
            map.put("duration", Utils.formatTime(Double.valueOf(next.getDuration()).intValue()));
            map.put("allTime",Double.valueOf(next.getDuration()).intValue());
            //寻找record表，章节的最高记录，不存在即没看0
            Record record = recordMapper.getRecord(memberId, next.getCourseId(), next.getChapterId());
            if (record != null) {
                map.put("currentTime",Integer.valueOf(record.getCurrentTime()));
                if (record.getIsWatched()) {
                    map.put("progress", 100);
                } else {
                    Integer currentTime = Integer.valueOf(record.getCurrentTime());
                    Integer allTime = Integer.valueOf(record.getAllTime());
                    int progress = Utils.toPercent(currentTime, allTime);
                    map.put("progress", progress);
                }
            } else {
                map.put("progress", 0);
                map.put("currentTime",0);
            }
            chapterList.add(map);
        }
        System.out.println(chapterList);
        return CommonResult.ok("获取章节列表成功", chapterList);
    }
}
