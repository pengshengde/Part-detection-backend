package com.example.springboot1.logging;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    public static final Logger log = LoggerFactory.getLogger(DemoService.class);

    public void testException(String name) {
        log.info(name.toString());

}

}
