package vip.wqby.learnserver.conf;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import vip.wqby.learnserver.utils.JWTUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Interceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //只拦截post和get
        if (!request.getMethod().equalsIgnoreCase("POST") && !request.getMethod().equalsIgnoreCase("GET")) {
            return true;
        }

        Map<String, Object> map = new HashMap<>();
        String token = request.getHeader("token") == null ? "" : request.getHeader("token");
        System.out.println("token");
        System.out.println(request.getHeader("token"));
        if (JWTUtil.verifyToken(token)) {
            return true;
        } else {
            // 封装返回值
            map.put("code", 4010);
            JSONObject json = new JSONObject(map);
            response.setContentType("application/json;charset=UTF-8");
            //表示接受任意域名的请求,也可以指定域名
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
            //该字段可选，是个布尔值，表示是否可以携带cookie
            response.setHeader("Access-Control-Allow-Credentials", "true");
            //允许跨域的请求方法
            response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
            //请求头信息
            response.setHeader("Access-Control-Allow-Headers", "*");

            PrintWriter writer = response.getWriter();
            writer.print(json);
            writer.flush();
            writer.close();
            return false;
        }


    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
