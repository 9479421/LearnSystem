package vip.wqby.learnserver.mapper.auto;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import vip.wqby.learnserver.model.auto.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author astupidcoder
 * @since 2022-11-02
 */
public interface RecordMapper extends BaseMapper<Record> {

    @Select("select * from record where memberId=#{memberId} and courseId=#{courseId} and chapterId=#{chapterId} order by currentTime desc limit 1")
    Record getRecord(@Param("memberId")String memberId, @Param("courseId")Integer courseId, @Param("chapterId")Integer chapterId);
}
