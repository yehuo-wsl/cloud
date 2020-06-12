package cm.yehuo.cloud.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroConfigServerApp {

    public static void main(String[] args) {
        SpringApplication.run(MicroConfigServerApp.class, args);
    }



}