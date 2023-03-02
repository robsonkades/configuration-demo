package com.example.portal;

import com.example.portal.scheduler.jobs.Service;
import com.example.portal.scheduler.jobs.ServiceContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PortalApplication {

    public static void main(String[] args) {
        ServiceContext context = new ServiceContext(new Service("platform", "portal"));
        ServiceContext.install(context);
        SpringApplication.run(PortalApplication.class, args);
    }

}
