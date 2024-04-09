package vip.wqby.learnserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import vip.wqby.learnserver.consumer.noteConsumer;
import vip.wqby.learnserver.data.constantData;
import vip.wqby.learnserver.mapper.auto.CourseMapper;
import vip.wqby.learnserver.model.auto.Member;
import vip.wqby.learnserver.mapper.auto.MemberMapper;
import vip.wqby.learnserver.pojo.CommonResult;
import vip.wqby.learnserver.service.IMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.wqby.learnserver.utils.JWTUtil;
import vip.wqby.learnserver.utils.TenCloudUtils;

import javax.annotation.Resource;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-10-18
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {
    @Resource
    MemberMapper memberMapper;
    @Resource
    TenCloudUtils tenCloudUtils;

    @Override
    public CommonResult login(String requestBody) {
        JSONObject jsonObject = JSON.parseObject(requestBody);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        QueryWrapper qw = new QueryWrapper();
        qw.eq("username", username);
        qw.eq("password", password);
        Member member = memberMapper.selectOne(qw);
        if (member == null) {
            return CommonResult.failed("账号或密码错误");
        } else {
            String token = JWTUtil.Token(member);
            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put("username", username);
            hashMap.put("avatar", member.getAvatar());
            hashMap.put("nickname", member.getNickname());
            hashMap.put("phone", member.getPhone());
            hashMap.put("token", token);
            return CommonResult.ok("登录成功", hashMap);
        }
    }

    @Override
    public CommonResult sendRegisterCode(String phone) {
        constantData.sendRegisterNote(phone);
        return CommonResult.ok("发送成功");
    }

    @Override
    public CommonResult register(String requestBody) {
        JSONObject jsonObject = JSON.parseObject(requestBody);
        System.out.println(jsonObject);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String phone = jsonObject.getString("phone");
        String captcha = jsonObject.getString("captcha");
        for (int i = 0; i < constantData.registerList.size(); i++) {
            //找到手机号 匹配验证码
            if (constantData.registerList.get(i).getPhone().equals(phone)) {
                if (constantData.registerList.get(i).getExpireTime() > System.currentTimeMillis() / 1000) {
                    //时间校验成功，校验验证码
                    if (captcha.equals(constantData.registerList.get(i).getCode())) {
                        //成功，添加用户
                        try {
                            QueryWrapper qw = new QueryWrapper();
                            qw.eq("username", username);
                            Member member = memberMapper.selectOne(qw);
                            if (member != null) {
                                //用户已存在
                                return CommonResult.failed("用户名已被占用");
                            }

                            memberMapper.insert(new Member(null, username, password, phone, "未命名", tenCloudUtils.defaultAvatarImg));

                        } catch (Exception e) {
                            e.printStackTrace();
                            return CommonResult.failed("注册异常");
                        }


                        return CommonResult.ok("注册成功");
                    } else {
                        //验证码错误
                        return CommonResult.failed("验证码错误");
                    }
                } else {
                    //过期
                    return CommonResult.failed("验证码过期");
                }
            }
        }
        //验证码错误
        return CommonResult.failed("验证码校验异常");
    }

    @Override
    public CommonResult uploadAvatar(MultipartFile image, String token) {
        try {
            byte[] uploadBytes = image.getBytes();
            String md5Hex = DigestUtils.md5Hex(uploadBytes);
            tenCloudUtils.uploadFile(image.getInputStream(),"/avatar/"+md5Hex+".png");

            String memberId = JWTUtil.getMemberIdFromToken(token);
            String path = tenCloudUtils.baseUrl+"/avatar/"+md5Hex+".png";
            memberMapper.updateAvatar(memberId,path);
            HashMap hashMap = new HashMap();
            hashMap.put("path",path);
            return CommonResult.ok("上传头像成功",hashMap);
        } catch (Exception e) {
            return CommonResult.failed("上传头像失败");
        }
    }

    @Override
    public CommonResult modifyInfo(String requestBody, String token) {
        try {
            JSONObject jsonObject = JSON.parseObject(requestBody);
            String nickname = jsonObject.getString("nickname");
            String phone = jsonObject.getString("phone"); //暂时用不到不修改手机号
            String memberId = JWTUtil.getMemberIdFromToken(token);
            memberMapper.updateNickname(memberId,nickname);

            HashMap hashMap = new HashMap();
            hashMap.put("nickname",nickname);
            return CommonResult.ok("修改信息成功",hashMap);
        } catch (Exception e) {
            return CommonResult.failed("修改信息失败");
        }
    }

    @Override
    public CommonResult modifyPass(String requestBody, String token) {
        try {
            JSONObject jsonObject = JSON.parseObject(requestBody);
            String username = jsonObject.getString("username");
            String password = jsonObject.getString("password");
            QueryWrapper qw = new QueryWrapper();
            qw.eq("username", username);
            qw.eq("password", password);
            Member member = memberMapper.selectOne(qw);
            if (member == null) {
                return CommonResult.failed("原密码错误");
            }
            String newPassword = jsonObject.getString("newPassword");
            memberMapper.updatePass(username,password,newPassword);
            return CommonResult.ok("修改成功");
        }catch (Exception e){
            return CommonResult.failed("修改密码失败");
        }
    }
}
