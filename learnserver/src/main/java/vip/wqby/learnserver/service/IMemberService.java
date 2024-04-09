package vip.wqby.learnserver.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;
import vip.wqby.learnserver.model.auto.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.wqby.learnserver.pojo.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-10-18
 */
public interface IMemberService extends IService<Member> {
    public CommonResult login(String requestBody);
    public CommonResult sendRegisterCode(String requestBody);
    public CommonResult register(String requestBody);
    public CommonResult uploadAvatar(MultipartFile image, String token);
    public CommonResult modifyInfo(String requestBody, String token);
    public CommonResult modifyPass(String requestBody, String token);
}
