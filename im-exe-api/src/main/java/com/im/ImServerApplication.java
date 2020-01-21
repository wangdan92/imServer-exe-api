package com.im;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author WD
 * @Description 启动类
 * @Date 14:42 2020/1/21
*/
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@MapperScan({"com.im.dao","com.im.mapper"})
@EnableSwagger2
public class ImServerApplication extends SpringBootServletInitializer implements CommandLineRunner {



    public static void main(String[] args) {
        SpringApplication.run(ImServerApplication.class, args);
    }


    @Override
    public void run(String... args) {
    }

}
