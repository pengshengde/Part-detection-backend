package com.example.springboot1.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot1.entity.client.Image;
import com.example.springboot1.mapper.ImageMapper;
import com.example.springboot1.service.IImageService;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements IImageService {



}
