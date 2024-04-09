package vip.wqby.learnserver.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UploadVideo {
    String uploadKeyPath;
    String digest;

}
