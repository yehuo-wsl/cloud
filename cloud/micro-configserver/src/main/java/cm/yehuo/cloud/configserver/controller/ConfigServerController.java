package cm.yehuo.cloud.configserver.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: 夜火
 * @Created Date: 2020年06月04日
 */
@RestController
@RefreshScope
public class ConfigServerController {

    @Value("${name}")
    private String name;

    @RequestMapping("/name")
    public String getName(){
        return name;
    }

}