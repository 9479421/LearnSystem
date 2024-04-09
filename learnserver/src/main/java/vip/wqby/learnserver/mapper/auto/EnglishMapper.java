package vip.wqby.learnserver.mapper.auto;

import org.apache.ibatis.annotations.Select;
import vip.wqby.learnserver.model.auto.English;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author astupidcoder
 * @since 2022-11-07
 */
public interface EnglishMapper extends BaseMapper<English> {
    @Select("select * from english order by rand() limit 1")
    English getRandomVideo();
}
