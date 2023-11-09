package com.example.springboot1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot1.Entity.Parts;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PartMapper extends BaseMapper<Parts> {
}
