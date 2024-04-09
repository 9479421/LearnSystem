package vip.wqby.learnserver.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private String type;
    private String rawQuestion;
    private String question;
    private String options;
    private String answer;
    private String courseName;
    private String platform;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;
}
