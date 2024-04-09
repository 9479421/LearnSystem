package vip.wqby.learnserver.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import vip.wqby.learnserver.model.auto.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.wqby.learnserver.pojo.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-11-04
 */
public interface ICommentService extends IService<Comment> {
    public CommonResult addComment(String token, String requestBody);
    public CommonResult getCommentList(String token, String courseId);
}
