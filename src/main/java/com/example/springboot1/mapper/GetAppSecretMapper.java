package com.example.springboot1.mapper;

import com.example.springboot1.entity.client.GetAppSecret;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface GetAppSecretMapper {
    /*
 *  用户登录
 *  @Param("userName") 用户账号信息
     根据userName查找用户信息对比账号是否注册如存此账号证明已注册
     则根据用户前台输入的密码与数据库密码进行对比
  * */
    @Select("SELECT * FROM sys_AppSecret WHERE appId=#{appId}")
    GetAppSecret getSecret(@Param("appId") String appId);

    @Select("SELECT EXISTS(SELECT 1 FROM sys_AppSecret WHERE appId=#{appId})")
    boolean existsAppId(@Param("appId") String appId);

    @Insert("Insert INTO sys_AppSecret (appId, appSecret) values (#{appId},#{appSecret})")
    int saveAppSecret(@Param("appId") String appId, @Param("appSecret")String appSecret);


    @Select("SELECT EXISTS(SELECT 1 FROM sys_AppSecret WHERE sign=#{sign})")
    boolean existsSign(@Param("sign") String sign);
}


