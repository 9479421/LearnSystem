package vip.wqby.learnserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import vip.wqby.learnserver.mapper.auto.MemberMapper;
import vip.wqby.learnserver.model.auto.Comment;
import vip.wqby.learnserver.mapper.auto.CommentMapper;
import vip.wqby.learnserver.model.auto.Member;
import vip.wqby.learnserver.pojo.CommonResult;
import vip.wqby.learnserver.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.wqby.learnserver.utils.JWTUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-11-04
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Resource
    CommentMapper commentMapper;
    @Resource
    MemberMapper memberMapper;
    @Override
    public CommonResult addComment(String token, String requestBody) {
        String memberId = JWTUtil.getMemberIdFromToken(token);
        JSONObject jsonObject = JSONObject.parseObject(requestBody);
        String courseId = jsonObject.getString("courseId");
        String content = jsonObject.getString("content");
        commentMapper.insert(new Comment(null,memberId,content,courseId, LocalDateTime.now()));
        return CommonResult.ok("评论成功");
    }
    @Override
    public CommonResult getCommentList(String token, String courseId) {
        String memberId = JWTUtil.getMemberIdFromToken(token);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("courseId",courseId);
        queryWrapper.orderByDesc("time");
        List<Comment> list = commentMapper.selectList(queryWrapper);
        List retList = new ArrayList();
        for (Comment comment : list){
            HashMap hashMap = new HashMap();
            hashMap.put("memberId",comment.getMemberId());
            hashMap.put("content",comment.getContent());
            hashMap.put("time",comment.getTime().toString());

            QueryWrapper queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("id",comment.getMemberId());
            Member member = memberMapper.selectOne(queryWrapper1);

            hashMap.put("nickname",member.getNickname());
            hashMap.put("username",member.getUsername());
            hashMap.put("avatar",member.getAvatar());

            retList.add(hashMap);
        }
        return CommonResult.ok("查询成功",retList);
    }
}
