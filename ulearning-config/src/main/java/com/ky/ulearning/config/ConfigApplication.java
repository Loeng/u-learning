package com.ky.ulearning.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.util.StringUtils;

/**
 * @author luyuhao
 * @date 19/11/28 17:19
 */
@EnableConfigServer
@SpringBootApplication
@EnableEurekaClient
public class ConfigApplication {
    public static void main(String[] args) {
        setLocation();
        SpringApplication.run(ConfigApplication.class, args);
    }

    private static void setLocation(){
        String path = System.getenv("ulearning");
        if(StringUtils.isEmpty(path)){
            path = "local";
        }
        System.setProperty(ConfigFileApplicationListener.CONFIG_LOCATION_PROPERTY, "classpath:/config/" + path + "/");
        System.setProperty(ConfigFileApplicationListener.ACTIVE_PROFILES_PROPERTY, path);
    }
}
