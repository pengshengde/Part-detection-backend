package com.example.springboot1.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot1.Entity.Parts;
import com.example.springboot1.mapper.PartMapper;
import com.example.springboot1.service.IPartService;
import org.springframework.stereotype.Service;

@Service
public class PartServiceImpl extends ServiceImpl<PartMapper, Parts> implements IPartService {

}
