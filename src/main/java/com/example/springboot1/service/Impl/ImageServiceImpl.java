package com.example.springboot1.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot1.Entity.Image;
import com.example.springboot1.mapper.GetImageMapper;
import com.example.springboot1.mapper.ImageMapper;
import com.example.springboot1.service.IImageService;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements IImageService {

}
