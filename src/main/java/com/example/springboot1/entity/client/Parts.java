package com.example.springboot1.entity.client;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.springboot1.entity.client.TypeHandler.StringTypeHandler;
import lombok.Data;

import java.util.List;

/*
 *  该类用于存放零件信息，用于向前端软件传递信息的实体类
 * */
@Data
@TableName(value = "sys_parts", autoResultMap = true)
public class Parts {
    @TableId( type = IdType.AUTO)
    private Integer id;

    @TableField("part_id")
    private String part_id;      // 零件编号

    @TableField(typeHandler = StringTypeHandler.class)
    private List<String> defect_type;   // 含有的缺陷类别

    private String detect_time;    // 零件检测时间

}
