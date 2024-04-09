package vip.wqby.learnserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import vip.wqby.learnserver.model.auto.Record;
import vip.wqby.learnserver.mapper.auto.RecordMapper;
import vip.wqby.learnserver.pojo.CommonResult;
import vip.wqby.learnserver.service.IRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.wqby.learnserver.utils.JWTUtil;
import vip.wqby.learnserver.utils.logUtils.qLog;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-11-02
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {
    @Resource
    RecordMapper recordMapper;

    @Override
    public CommonResult updateRecord(String token, String courseId, String chapterId, String currentTime, String allTime) {
        String memberId = JWTUtil.getMemberIdFromToken(token);
        qLog.info(courseId,chapterId,currentTime,allTime);
        if (courseId.equals("") || chapterId.equals("") || currentTime.equals("") || allTime.equals("")){
            return CommonResult.failed("保存失败");
        }


        String currentTime_int = String.valueOf(Double.valueOf(currentTime).intValue());
        String allTime_int = String.valueOf(Double.valueOf(allTime).intValue());
        //查询是否有记录
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("memberId", memberId);
        queryWrapper.eq("courseId", courseId);
        queryWrapper.eq("chapterId", chapterId);
        queryWrapper.orderByDesc("currentTime");
        Record longestRecord = recordMapper.selectOne(queryWrapper);
        if (longestRecord == null) {
            boolean isWatched = false;
            //插入
            if (Double.valueOf(currentTime_int) >= Double.valueOf(allTime_int) * 0.95) {
                isWatched=true;
            }else{
                isWatched=false;
            }
            Record record = new Record(null, memberId, courseId, chapterId, currentTime_int, allTime_int, isWatched);
            recordMapper.insert(record);
        } else {
            //没看过才需要更新
            if (longestRecord.getIsWatched()==false){
                //更新
                UpdateWrapper updateWrapper = new UpdateWrapper();
                updateWrapper.eq("id", longestRecord.getId());
                updateWrapper.eq("courseId", courseId);
                updateWrapper.eq("memberId", memberId);
                updateWrapper.eq("chapterId", chapterId);
                updateWrapper.set("currentTime", currentTime_int);
                updateWrapper.set("allTime", allTime_int);
                //看完0.95就算完
                if (Double.valueOf(currentTime_int) >= Double.valueOf(allTime_int) * 0.95) {
                    updateWrapper.set("isWatched", true);
                }else{
                    updateWrapper.set("isWatched", false);
                }
                recordMapper.update(null, updateWrapper);
            }

        }
        return CommonResult.ok("更新进度成功");
    }
}
