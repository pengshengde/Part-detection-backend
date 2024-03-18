package com.example.springboot1.entity.client;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;


@Data
@TableName("sys_AppSecret")
@AllArgsConstructor
@NoArgsConstructor
public class GetAppSecret {
    private String AppSecret;

}
