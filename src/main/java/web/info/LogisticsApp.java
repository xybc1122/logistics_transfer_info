package web.info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//直接右键 run 启动Tomcat 容器
@SpringBootApplication //这个注解是springboot 帮你自动配置到spirng 容器里配置
//启动定时注解
@EnableScheduling
public class LogisticsApp {

    public static  void main(String[] args){
        SpringApplication.run(LogisticsApp.class, args);
    }
}
