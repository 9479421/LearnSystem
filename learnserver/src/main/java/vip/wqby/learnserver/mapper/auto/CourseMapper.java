package vip.wqby.learnserver.mapper.auto;

import org.apache.ibatis.annotations.*;
import vip.wqby.learnserver.model.auto.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import vip.wqby.learnserver.model.auto.Course_RET;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author astupidcoder
 * @since 2022-09-12
 */

public interface CourseMapper extends BaseMapper<Course> {
    int addCourse(Course course);

    @Update("update course set name=#{name},coverImg=#{coverImg},teacher=#{teacher},intro=#{intro} where courseId=#{courseId}")
    int changeCourseInfo(@Param("courseId")String courseId,@Param("name")String name,@Param("coverImg")String coverImg,@Param("teacher")String teacher,@Param("intro")String intro);

    @Update("update course set isPutAway=#{status} where courseId=#{courseId}")
    int changePutAwayStatus(@Param("courseId")String courseId,@Param("status")String status);

    @Update("update course set coverImg=#{newCoverImg} where courseId=#{courseId}")
    int updateCoverImg(@Param("courseId")String courseId,@Param("newCoverImg")String newCoverImg);


    @Select("select *,if(courseId in (select courseId from mycourse where memberId=#{memberId}),true,false) 'isAdd' from course")
    List<Course_RET> listCourse(@Param("memberId")String memberId);
}
