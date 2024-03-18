package com.example.springboot1.service.Impl;

import com.example.springboot1.common.browser.core.domain.BaseEntity;
import com.example.springboot1.common.browser.utils.StringUtils;
import com.example.springboot1.config.LocalConfig;
import com.example.springboot1.entity.browser.quality.*;
import com.example.springboot1.entity.client.ImageInfo;
import com.example.springboot1.mapper.*;
import com.example.springboot1.service.ISysImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


/**
 *  图片管理(SysImage)表服务实现类
 */
@Service
public class SysImageServiceImpl implements ISysImageService
{
    @Autowired
    private SysImageMapper imageMapper;

    @Autowired
    private SysDefectTypeMapper defectTypeMapper;

    @Autowired
    private SysImageDefectTypeMapper imageDefectTypeMapper;

    @Autowired
    private SysPartImageMapper partImageMapper;

    @Autowired
    private SysPartDefectTypeMapper partDefectTypeMapper;

    @Autowired
    LocalConfig localConfig;

    /**
     * 根据条件查询检测图片的数据
     *
     * @param sysImage 检测图片的实体类
     * @return 图片的信息集合
     */
    @Override
    public List<SysImage> selectImageList(SysImage sysImage){
        return imageMapper.selectAllocatedImageList(sysImage);
    }


    /**
     * 检查图片的名称是否唯一
     * @param image
     * @return
     */
    @Override
    public boolean checkImageNameUnique(SysImage image)
    {
        Long imageId = StringUtils.isNull(image.getImageId()) ? -1L : image.getImageId();
        SysImage info = imageMapper.checkImageNameUnique(image.getImageName());
        if (StringUtils.isNotNull(info) && info.getImageId().longValue() != imageId.longValue())
        {
            return false;
        }
        return true;
    }


    /**
     * 插入新图片信息和图片与缺陷相连表的逻辑
     *
     * @param image
     * @return
     */
    @Override
    @Transactional
    public int insertImage(SysImage image)
    {
        // 先更新图片信息
        int rows = imageMapper.insertImage(image);
        // 获取图片的id
        Long imageId = this.selectIdByImageName(image);

        Long partId = image.getPartId();

        // 根据defectTypeNames 查询 defectTypeIds
        String[] defectTypeNames = image.getDefectTypeNames();

        // 判断defectTypeNames是否为空，若不为则说明存在缺陷，若为这说明正常
        if (defectTypeNames != null && defectTypeNames.length > 0)
        {
            // 插入图片存在的缺陷
            this.batchInsertImageDefectType(imageId, defectTypeNames);
            // 检查零件是否为正常
            // 如果零件是正常的， 则需要删除正常信息，导入缺陷信息
            // 如果零件已经存在缺陷，则需要进行比较，导入缺少的缺陷类型
            this.batchInsertPartDefectType(partId,defectTypeNames);


        }else
        {
            // 说明图片没有缺陷，则返回正常
            this.insertNormalImageDefectType(imageId);
            // 检测图片不存在缺陷时， 零件缺陷信息不需要变换
            // 检查图片是否已被检测过，若没有则将设置为正常
            this.checkPartDefectType(partId);

        }

        // 下面插入零件与零件图片相关联的表
        SysPartImage partImage = new SysPartImage();
        partImage.setImageId(imageId);
        partImage.setPartId(partId);
        partImageMapper.insertPartImage(partImage);


        return rows;
    }


    /**
     * 将文件写入服务器指定目录下
     *
     * @param file  图片的数据信息
     * @param imageTag 图片标签
     * @return 图片的url, 图片的新名称
     */
    @Override
    public Map<String, String> uploadImage(MultipartFile file, String imageTag)
    {
        String fileName = file.getOriginalFilename();
        String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String fileNamePrefix = fileName.substring(0, fileName.lastIndexOf("."));

        LocalDateTime localDateTime = LocalDateTime.now();
        Integer Year = localDateTime.getYear();
        Integer Month = localDateTime.getMonthValue();

        long timestamp = System.currentTimeMillis();                     // 获取当前的时间戳

        String sepa = File.separator;

        String baseFilePath = localConfig.getUploadFilePath();
        String filePath = baseFilePath + sepa + imageTag + sepa + Year + sepa + Month;
        createDirectory(filePath);

        String uuidString = UUID.randomUUID().toString();

        String newImageName = timestamp + "-" + uuidString + "." + fileNameSuffix;
        File targetFile = new File(filePath + sepa + newImageName);

        try {
            //将上传文件写到服务器上指定的文件
            file.transferTo(targetFile);
            String saveUrl =localConfig.getBaseurl() + imageTag + "/" +  Year + "/" + Month + "/" + newImageName;

            Map<String,String> map = new HashMap<String,String>(2);

            map.put("imageUrl",saveUrl);
            map.put("imageRename",newImageName);
            ImageInfo imageInfo = new ImageInfo();

            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<String,String>(0);
        }

    }

    /**
     * 批量新增图片的缺陷信息
     * @param imageId
     * @param defectTypeNames
     */
    public void batchInsertImageDefectType(Long imageId, String[] defectTypeNames)
    {
        // 插入图片存在的缺陷
        List<SysImageDefectType> list = new ArrayList<SysImageDefectType>(defectTypeNames.length);
        for (String defectTypeName : defectTypeNames)
        {
            SysImageDefectType ImageDefectType = new SysImageDefectType();
            ImageDefectType.setImageId(imageId);
            ImageDefectType.setDefectTypeId(defectTypeMapper.selectDefectTypeIdByName(defectTypeName));
            list.add(ImageDefectType);
        }
        imageDefectTypeMapper.batchInsertImageDefectType(list);
    }

    /**
     * 新增图片的的正常信息
     * @param imageId
     */
    public void insertNormalImageDefectType(Long imageId)
    {
        // 说明图片没有缺陷，则返回正常
        SysImageDefectType ImageDefectType = new SysImageDefectType();
        ImageDefectType.setImageId(imageId);
        ImageDefectType.setDefectTypeId(defectTypeMapper.selectDefectTypeIdByName("zhengchang"));
        imageDefectTypeMapper.insertImageDefectType(ImageDefectType);
    }

    /**
     * 批量更新零件的缺陷信息
     * @param partId
     * @param defectTypeNames
     */
    public void  batchInsertPartDefectType(Long partId, String[] defectTypeNames)
    {
        SysPartDefectType PartDefectType = new SysPartDefectType();
        PartDefectType.setPartId(partId);
        PartDefectType.setDefectTypeId(defectTypeMapper.selectDefectTypeIdByName("zhengchang"));
        // 检查零件原先是否正常，若为正常，则删除正常信息
        if (partDefectTypeMapper.countPartDefectType(PartDefectType) > 0)
        {
            partDefectTypeMapper.deletePartDefectTypeInfo(PartDefectType);
        }

        // 批量插入图片缺陷
        List<SysPartDefectType> list = new ArrayList<SysPartDefectType>();
        for (String defectTypeName : defectTypeNames)
        {
            PartDefectType.setDefectTypeId(defectTypeMapper.selectDefectTypeIdByName(defectTypeName));
            // 检查零件是否存在这种缺陷,不存在则需要添加该缺陷
            if (partDefectTypeMapper.countPartDefectType(PartDefectType) == 0)
            {
                list.add(PartDefectType);
            }
        }
        // 最后批量插入/更新零件的缺陷信息
        partDefectTypeMapper.batchInsertPartDefectType(list);
    }

    /**
     * 检查是否已经检测过该部件的图片，
     * 若无则新增正常，有则不做处理
     */
    public void checkPartDefectType(Long partId)
    {
        SysPartDefectType PartDefectType = new SysPartDefectType();
        PartDefectType.setPartId(partId);
        // 检查部件是否已经检测过
        if (partDefectTypeMapper.countPartDefectType(PartDefectType) == 0)
        {
            // 没有检查过，默认为正常
            PartDefectType.setDefectTypeId(defectTypeMapper.selectDefectTypeIdByName("zhengchang"));
        }
    }

    /**
     * 检查文件夹是否存在，不存在则创建
     * @param directoryPath
     */
    public void createDirectory(String directoryPath){
        File targetFile = new File(directoryPath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
    }

    /**
     * 通过图片名称寻找图片的Id
     * @param image
     * @return
     */
    public Long selectIdByImageName(SysImage image) {
        return imageMapper.selectIdByImageName(image.getImageName());
    }

    @Override
    public SysImage selectImageById(Long imageId){
        SysImage image = imageMapper.selectImageByImageId(imageId);
        return changeDefectTypeName(image);
    }


    /**
     * 删除图片并更新零件
     * @param image
     * @return
     */
    @Override
    @Transactional
    public int deleteImage(SysImage image)
    {
        // 根据图片名字找到图片的id
        if (image.getImageId() == null)
        {
            image.setImageId(this.selectIdByImageName(image));;
        }


        // 先根据图片找到零件的Id
        SysPartImage partImage = partImageMapper.selectPartImageByImageId(image.getImageId());
        if (partImage.getPartId() != null )
        {
            // 更新零件的缺陷信息
            this.updatePartDefectType(partImage);
            // 删除零件与图片关联信息
            partImageMapper.deletePartImageInfo(partImage);
        }


        // 删除图片与缺陷的关联信息
        imageDefectTypeMapper.deleteImageDefectTypeByImageId(image.getImageId());

        // 删除图片的信息
        int rows = imageMapper.deleteImageById(image.getImageId());

        return rows;
    }


    /**
     * 删除图片时更新零件的缺陷信息
     * @param partImage
     */
    public void updatePartDefectType(SysPartImage partImage)
    {
        SysPartDefectType partDefectType = new SysPartDefectType();
        partDefectType.setPartId(partImage.getPartId());
        partDefectType.setDefectTypeId(defectTypeMapper.selectDefectTypeIdByName("zhengchang"));

        // 检查零件是否正常
        if (partDefectTypeMapper.countPartDefectType(partDefectType) > 0)
        {
            return;
        }

        SysImageDefectType imageDefectType = new SysImageDefectType();
        imageDefectType.setImageId(partImage.getImageId());
        imageDefectType.setDefectTypeId(defectTypeMapper.selectDefectTypeIdByName("zhengchang"));
        // 检查图片是否正常
        if (imageDefectTypeMapper.countImageDefectType(imageDefectType) > 0)
        {
            return;
        }

        // 获取零件的所有图片Id
        List<Long> imageIdList = partImageMapper.selectImageIdsByPartId(partImage.getPartId());

        // 获取图片所有缺陷的Id
        List<Long> defectTypeIdList = imageDefectTypeMapper.selectDefectTypeByImageId(partImage.getImageId());
        for (Long defectTypeId : defectTypeIdList)
        {
            for (Long imageId : imageIdList)
            {
                imageDefectType.setImageId(imageId);
                imageDefectType.setDefectTypeId(defectTypeId);;

                // 检查零件的其他图片是否存在该缺陷
                if (!Objects.equals(imageId, partImage.getImageId()) && imageDefectTypeMapper.countImageDefectType(imageDefectType) > 0)
                {
                    return;
                }

                // 零件更新缺陷
                partDefectType.setDefectTypeId(defectTypeId);
                partDefectTypeMapper.deletePartDefectTypeInfo(partDefectType);

                // 检查零件的缺陷是否已经全部删除
                partDefectType.setDefectTypeId(null);
                if (partDefectTypeMapper.countPartDefectType(partDefectType) == 0)
                {
                    partDefectType.setDefectTypeId(defectTypeMapper.selectDefectTypeIdByName("zhengchang"));
                    partDefectTypeMapper.insertPartDefectType(partDefectType);
                }

                return;
            }
        }

    }

    /**
     * 根据条件分页查询零件的图片
     */
    @Override
    public List<SysImage> selectAllocatedImageList(SysImage image)
    {
        return imageMapper.selectAllocatedImageList(image);
    }

    @Override
    public List<SysImage> selectUnallocatedImageList(SysImage image)
    {
        return imageMapper.selectUnallocatedImageList(image);
    }

    public SysImage changeDefectTypeName(SysImage image)
    {
        List<SysDefectType> defectTypes = image.getDefectTypes();
        if (defectTypes != null) {
            List<String> defectTypeNamesList = new ArrayList<>();
            for (SysDefectType defectType : defectTypes) {
                String defectTypeName = defectType.getDefectDescription();
                defectTypeNamesList.add(defectTypeName);
            }
            String[] defectTypeNamesArray = defectTypeNamesList.toArray(new String[0]);
            String defectTypeName = String.join(",", defectTypeNamesList);
            image.setDefectTypeNames(defectTypeNamesArray);
            image.setDefectTypeName(defectTypeName);
        }

        return image;
    }

}
