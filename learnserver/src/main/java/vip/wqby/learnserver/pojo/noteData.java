package vip.wqby.learnserver.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class noteData {
    private String phone;
    private String code;
    private Long expireTime;


}
