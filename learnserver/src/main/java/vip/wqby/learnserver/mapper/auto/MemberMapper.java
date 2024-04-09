package vip.wqby.learnserver.mapper.auto;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import vip.wqby.learnserver.model.auto.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author astupidcoder
 * @since 2022-10-18
 */
public interface MemberMapper extends BaseMapper<Member> {
    @Update("update member set avatar=#{avatar} where id=#{id}")
    int updateAvatar(@Param("id")String id,@Param("avatar")String avatar);
    @Update("update member set nickname=#{nickname} where id=#{id}")
    int updateNickname(@Param("id")String id,@Param("nickname")String nickname);
    @Update("update member set password=#{newPassword} where username=#{username} and password=#{password}")
    int updatePass(@Param("username")String username,@Param("password")String password,@Param("newPassword")String newPassword);
}
