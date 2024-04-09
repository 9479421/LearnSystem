package vip.wqby.learnserver.consumer;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import vip.wqby.learnserver.data.constantData;
import vip.wqby.learnserver.pojo.noteData;
import vip.wqby.learnserver.utils.logUtils.qLog;

import java.util.Iterator;

public class noteConsumer extends Thread {

    public static void sendNote(String templateId, String[] args, String phone) {
        try {
            Credential cred = new Credential("", "");
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setReqMethod("POST");
            httpProfile.setConnTimeout(60);
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
            SendSmsRequest req = new SendSmsRequest();
            String sdkAppId = "1400665385";
            req.setSmsSdkAppId(sdkAppId);
            String signName = "王国权个人学习网";
            req.setSignName(signName);
            req.setTemplateId(templateId);
            req.setTemplateParamSet(args);
            String[] phoneNumberSet = {"+86" + phone};
            req.setPhoneNumberSet(phoneNumberSet);
            SendSmsResponse res = client.SendSms(req);
            System.out.println(SendSmsResponse.toJsonString(res));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        for (; ; ) {
            //注册的
            try {
                noteData poll = constantData.registerQueue.take();
                if (poll != null) {
                    boolean flag = false;
                    for (int i = 0; i < constantData.registerList.size(); i++) {
                        if (poll.getPhone().equals(constantData.registerList.get(i).getPhone()) ||
                                constantData.registerList.get(i).getExpireTime() < System.currentTimeMillis() / 1000) {
                            //已有手机号，重置时间
                            constantData.registerList.get(i).setExpireTime(System.currentTimeMillis() / 1000 + 60);
                            flag = true;
                            break;
                        }
                    }
                    //不存在手机号，新增
                    if(flag==false){ constantData.registerList.add(poll);}
                    //发送短信
                    qLog.info(poll.toString());
                    sendNote("1582677", new String[]{poll.getCode()}, poll.getPhone());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
