package com.example.springboot1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/DM")
public class DbController {

    /**
     * 注入 jdbcTemplate 模板对象
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/queryDbVersion")
    public List queryDbVersion() {
        return jdbcTemplate.queryForList(
                "SELECT banner as 版本信息 FROM v$version");
    }
}
