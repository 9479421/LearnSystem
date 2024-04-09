package vip.wqby.learnserver.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String msg;
    private T data;

    public CommonResult(Integer code, String msg){
        this(code,msg,null);
    }

    public static CommonResult failed(String msg){
        return new CommonResult(444,msg,null);
    }

    public static CommonResult warning(String msg){
        return new CommonResult(201,msg,null);
    }

    public static CommonResult ok(String msg){
        return new CommonResult(200,msg,null);
    }
    public static CommonResult ok(String msg,Object data){
        return new CommonResult(200,msg,data);
    }
}
