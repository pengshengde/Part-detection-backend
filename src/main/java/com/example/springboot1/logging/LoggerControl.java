package com.example.springboot1.logging;

import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerControl {
    private DemoService demoService;

    @RequestMapping(value = "testException")
    public Object testException(String name) {
        demoService.testException(name);
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
