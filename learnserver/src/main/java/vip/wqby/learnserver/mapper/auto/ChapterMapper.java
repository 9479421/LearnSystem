package vip.wqby.learnserver.mapper.auto;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import vip.wqby.learnserver.model.auto.Chapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import vip.wqby.learnserver.model.auto.Course;
import vip.wqby.learnserver.pojo.CommonResult;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author astupidcoder
 * @since 2022-09-17
 */
public interface ChapterMapper extends BaseMapper<Chapter> {
    @Insert("insert into chapter (chapterId,digest,path,courseId,chapterName,duration) values (#{chapterId},#{digest},#{path},#{courseId},#{chapterName},#{duration})")
    int addChapter(Chapter chapter);

    @Select("select count(*) from chapter where courseId = #{courseId}")
    int getChapterCounts(@Param("courseId") int courseId);
}
