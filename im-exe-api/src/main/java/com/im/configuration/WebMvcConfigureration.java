package com.im.configuration;
import com.im.interceptor.AuthHanlerInterceptor;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.MultipartConfigElement;

/**
 * @author ZhangPeng
 * @date 2017/12/8
 * 拦截器
 */
@Configuration
public class WebMvcConfigureration implements WebMvcConfigurer {

    @Bean
    AuthHanlerInterceptor authHanlerInterceptor() {
        return new AuthHanlerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authHanlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/roleLogin/**").excludePathPatterns("/login/**").excludePathPatterns("/loginByToken/**").excludePathPatterns("/oaLogin/**").excludePathPatterns("/oaLoginByToken/**")
                .excludePathPatterns("/getFileFromEncodeParam/**").excludePathPatterns("/interface/**") .excludePathPatterns("/getFile/**").excludePathPatterns("/getFileFromParam/**").excludePathPatterns("/modifyPassword")
                .excludePathPatterns("/swagger*/**").excludePathPatterns("/v2/**").excludePathPatterns("/webjars/**").excludePathPatterns("/sms/common/getSMS").excludePathPatterns("/sms/checkVerifyCode/**").excludePathPatterns("/redis/**")
                .excludePathPatterns("/visitor/common/detailByCode").excludePathPatterns("/visitor/common/detail").excludePathPatterns("/visitor/common/complete").excludePathPatterns("/wxPayNotify/notify/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT","OPTIONS")
                .maxAge(3600);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize(DataSize.parse("100MB"));
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.parse("100MB"));
        return factory.createMultipartConfig();
    }

}
