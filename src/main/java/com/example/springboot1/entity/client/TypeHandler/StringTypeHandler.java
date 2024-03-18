package com.example.springboot1.entity.client.TypeHandler;

import com.alibaba.fastjson2.TypeReference;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.util.List;

@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class StringTypeHandler extends ListTypeHandler<String>{
    // 将ListTypeHandler<T>（T为任意对象），具体为特定的对象String
    @Override
    protected TypeReference<List<String>> specificType() {
        return new TypeReference<List<String>>() {
        };
    }

}
