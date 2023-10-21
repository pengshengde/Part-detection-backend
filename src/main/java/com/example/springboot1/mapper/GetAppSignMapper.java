package com.example.springboot1.mapper;

import com.example.springboot1.Entity.GetAppSecret;
import com.example.springboot1.Entity.GetAppSign;
import com.example.springboot1.Entity.GetSign;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface GetAppSignMapper {

//    @Save("SELECT * FROM sys_AppSecret WHERE AppId=#{AppId}")

    @Insert("Insert INTO sys_AppSignHistory (appId, timestamp, appSecret,sign) values (#{appId},#{timestamp},#{appSecret},#{sign})")
    int saveSign(@Param("appId") String appId, @Param("timestamp")String timestamp, @Param("appSecret")String appSecret,
              @Param("sign")String sign);

    @Update("update sys_AppSecret set sign = #{sign} where appId = #{appId}")
    boolean updateSign(@Param("appId") String appId,@Param("sign")String sign);

}
