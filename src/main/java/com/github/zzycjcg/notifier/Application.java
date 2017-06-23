package com.github.zzycjcg.notifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by zzy on 2017/3/1.
 */
@Configuration
@SpringBootApplication
@ImportResource("classpath*:spring-project.xml")
public class Application {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
