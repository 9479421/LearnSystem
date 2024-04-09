package vip.wqby.learnserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import vip.wqby.learnserver.mapper.auto.MycourseMapper;
import vip.wqby.learnserver.model.auto.Course;
import vip.wqby.learnserver.mapper.auto.CourseMapper;
import vip.wqby.learnserver.model.auto.Mycourse;
import vip.wqby.learnserver.pojo.CommonResult;
import vip.wqby.learnserver.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.wqby.learnserver.utils.JWTUtil;
import vip.wqby.learnserver.utils.TenCloudUtils;
import vip.wqby.learnserver.utils.logUtils.qLog;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-09-12
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Resource
    CourseMapper courseMapper;
    @Resource
    MycourseMapper myCourseMapper;
    @Autowired
    TenCloudUtils tenCloudUtils;

    @Override
    public CommonResult getCourseList(String searchType, String searchText) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if ((searchType != null && searchText != null) &&
                (searchType.equals("courseId")
                        || searchType.equals("name")
                        || searchType.equals("teacher")
                        || searchType.equals("coverImg"))) {
            queryWrapper.like(searchType, searchText);
        }
        return new CommonResult(200, "查询课程成功", courseMapper.selectList(queryWrapper));
    }

    @Override
    public CommonResult addCourse(String requestBody) {
        qLog.info(requestBody);
        JSONObject json = JSONObject.parseObject(requestBody);
        String name = json.getString("name");
        String coverImg = json.getString("coverImg");
        String teacher = json.getString("teacher");
        String intro = json.getString("intro");
        qLog.info(name, coverImg, teacher, intro);
        //先插入一个默认图片地址
        Course course = new Course(null, name, teacher, tenCloudUtils.defaultCoverImg, intro, null);
        int ret = courseMapper.addCourse(course);
        qLog.info(String.valueOf(ret), course.toString());
        if (ret > 0) {
            //上传图片
            try {
                String keyPath = "/" + course.getCourseId() + "/cover.png";
                tenCloudUtils.uploadFile(coverImg, keyPath);
                //修改新的coverImg
                courseMapper.updateCoverImg(String.valueOf(course.getCourseId()), tenCloudUtils.baseUrl + keyPath);
            } catch (Exception e) {
                qLog.info("上传封面失败,不删除数据");
                return new CommonResult(201, "上传封面图片异常");
            }
            return new CommonResult(200, "添加成功");
        } else {
            return new CommonResult(444, "添加失败");
        }
    }

    @Override
    public CommonResult changeCourseInfo(String requestBody) {
        qLog.info(requestBody);
        JSONObject json = JSONObject.parseObject(requestBody);
        String courseId = json.getString("courseId");
        String name = json.getString("name");
        String coverImg = json.getString("coverImg");

        //上传图片
        String keyPath = "/" + courseId + "/cover.png";
        try {
            tenCloudUtils.uploadFile(coverImg, keyPath);
            //修改新的coverImg
            qLog.info("修改", courseId, tenCloudUtils.baseUrl + keyPath);
            courseMapper.updateCoverImg(courseId, tenCloudUtils.baseUrl + keyPath);
        } catch (Exception e) {
            e.printStackTrace();
            //上传封面失败
            qLog.info("上传封面失败");
            return new CommonResult(444, "上传封面图片异常");
        }

        String teacher = json.getString("teacher");
        String intro = json.getString("intro");
        qLog.info(name, coverImg, teacher, intro);
        int ret = courseMapper.changeCourseInfo(courseId, name, tenCloudUtils.baseUrl + keyPath, teacher, intro);
        if (ret == 1) {
            return new CommonResult(200, "修改成功");
        } else {
            return new CommonResult(444, "修改失败");
        }
    }

    @Override
    public CommonResult changePutAwayStatus(String courseId, String status) {
        int ret = courseMapper.changePutAwayStatus(courseId, status);
        if (ret == 1) {
            return new CommonResult(200, "修改成功");
        } else {
            return new CommonResult(444, "修改失败");
        }
    }

    @Override
    public CommonResult deleteCourse(String courseId) {
        try {
            String keyPath = "/" + courseId;
            tenCloudUtils.deleteFolder(keyPath);
        } catch (Exception e) {
            return new CommonResult(444, "删除云数据失败");
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("courseId", courseId); //通过wrapper设置条件
        int ret = courseMapper.delete(wrapper);
        if (ret == 1) {
            return new CommonResult(200, "删除成功");
        } else {
            return new CommonResult(444, "删除失败");
        }
    }

    @Override
    public CommonResult listCourse(String token) {
        String memberId = JWTUtil.getMemberIdFromToken(token);
        return new CommonResult(200, "查询课程成功", courseMapper.listCourse(memberId));
    }

    @Override
    public CommonResult addCourse(String token, String courseId) {
        String memberId = JWTUtil.getMemberIdFromToken(token);
        myCourseMapper.insert(new Mycourse(null, courseId, memberId));
        return CommonResult.ok("添加课程成功");
    }


}
