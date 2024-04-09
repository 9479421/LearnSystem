package vip.wqby.learnserver.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 用户拦截器
        registry.addInterceptor(new Interceptor())
                .excludePathPatterns("/client/member/**")
                .excludePathPatterns("/client/question/**")
                .excludePathPatterns("/manager/**")
                .addPathPatterns("/**");

//                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**","/doc.html/**")
        ;
    }


}
