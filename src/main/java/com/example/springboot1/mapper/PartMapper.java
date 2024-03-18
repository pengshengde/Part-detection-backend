package com.example.springboot1.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springboot1.entity.browser.common.SysUser;
import com.example.springboot1.entity.client.Parts;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PartMapper extends BaseMapper<Parts> {
}
