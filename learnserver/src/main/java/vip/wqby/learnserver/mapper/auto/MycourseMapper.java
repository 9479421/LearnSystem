package vip.wqby.learnserver.mapper.auto;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import vip.wqby.learnserver.model.auto.Chapter;
import vip.wqby.learnserver.model.auto.Course;
import vip.wqby.learnserver.model.auto.Mycourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author astupidcoder
 * @since 2022-10-20
 */
public interface MycourseMapper extends BaseMapper<Mycourse> {
    @Select("select * from course where courseId in (select courseId from mycourse where memberId = #{memberId})")
    List<Course> getMyCourseList(@Param("memberId")String memberId);

    @Select("select * from chapter where courseId=#{courseId}")
    List<Chapter> getMyChapterList(@Param("courseId")String courseId);


}
