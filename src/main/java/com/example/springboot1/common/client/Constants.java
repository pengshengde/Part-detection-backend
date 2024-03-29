package com.example.springboot1.common.client;

public interface Constants {

    String CODE_200 = "2000"; //服务器成功返回用户请求的数据
    String CODE_201 = "201"; // 该请求已成功，并因此创建了一个新的资源。
    String CODE_401 = "401";  // 签名校验未通过
    String CODE_400 = "400";  // 参数错误,由于被认为是客户端错误
    String CODE_402 = "402";  // 访问过期
    String CODE_403 = "403"; // 客户端没有访问内容的权限
    String CODE_404 = "404"; // 服务器找不到请求的资源
    String CODE_405 = "405";   // 服务器知道请求方法，但目标资源不支持该方法
    String CODE_500 = "500"; // 系统错误
    String CODE_600 = "600"; // 其他业务异常


    String CODE_2000 = "2000";  // 服务器成功返回用户请求的数据
    String CODE_2001 = "2001"; // 用户新建或修改数据成功
    String CODE_2002 = "2002";  // 表示一个请求已经进入后台排队（异步任务）
    String CODE_2003 = "2003"; // 服务器成功返回用户请求,无需返回参数
    String CODE_2004 = "2004";  // 用户删除数据成功
    String CODE_4000 = "4000"; // 用户发出的请求有错误，服务器没有进行新建或修改数据的操作
    String CODE_4001 ="4001"; // 表示用户没有权限（令牌、用户名、密码错误）
    String CODE_4002 = "4002";  // 表示用户访问过期(访问时间超过一定的时间)
    String CODE_4003 ="4003"; // 表示用户得到授权（与401错误相对），但是访问是被禁止的
    String CODE_4004 = "4004";  // 用户发出的请求针对的是不存在的记录，服务器没有进行操作
    String CODE_4005 = "4005";  // 方法不允许，服务器没有该方法
    String CODE_4006 = "4006";  // 用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）
    String CODE_4007 = "4007";  // 用户传过来的sign为空，请检查sign值
    String CODE_5000 = "5000";  // 服务器发生错误，用户将无法判断发出的请求是否成功

    String CODE_6000 = "6000"; // 图片的Url已存在
}
