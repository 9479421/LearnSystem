package vip.wqby.learnserver.service;

import vip.wqby.learnserver.model.auto.Record;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.wqby.learnserver.pojo.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-11-02
 */
public interface IRecordService extends IService<Record> {
    public CommonResult updateRecord(String token,String courseId,String chapterId,String currentTime,String allTime);
}
