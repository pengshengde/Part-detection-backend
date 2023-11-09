package com.example.springboot1.Entity.TypeHandler;

import com.alibaba.fastjson.TypeReference;

import java.util.List;

public class StringTypeHandler extends ListTypeHandler<String>{
    // 将ListTypeHandler<T>（T为任意对象），具体为特定的对象String
    @Override
    protected TypeReference<List<String>> specificType() {
        return new TypeReference<List<String>>() {
        };
    }

}
