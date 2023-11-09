package com.example.springboot1.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口统一返回包装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private String code;
    private String msg;
    private Object data;

    public static Result success() {
        return new Result(Constants.CODE_2000, "", null);
    }

    public static Result success(Object data) {
        return new Result(Constants.CODE_2000, "", data);
    }

    public static Result success(String msg, Object data){
        return new Result(Constants.CODE_2000, "", data);
    }


    public static Result successGetAppSecret(Object data) {return new Result(Constants.CODE_2000, "操作成功! 安全密钥已生成！", data);
    }
    public static Result successGetData(Object data) {
        return new Result(Constants.CODE_2000, "数据请求已完成！", data);
    }

    public static Result successSaveImage(Object data){
        return new Result(Constants.CODE_2000, "图片检测已完成", data);
    }
    public static Result successDelete(Object data) {
        return new Result(Constants.CODE_2004, "数据删除成功！", data);
    }
    public static Result errorSaveImage(){
        return new Result(Constants.CODE_4000, "保存图片失败，请重新上传", null);
    }
    public static Result errorGetData(){
        return new Result(Constants.CODE_4004, "未查询到该图片的检测结果", null);
    }

    public static Result errorDelete() {
        return new Result(Constants.CODE_4000, "数据删除失败，请检查传入参数",null);
    }
    public static Result errorSignTimeOut(){return new Result(Constants.CODE_4002, "访问过期，请重新访问", null);}
    public static Result errorSignJudge(){
        return new Result(Constants.CODE_4001, "权限不足，申请失败", null);
    }
    public static Result errorSignIsnull(){
        return new Result(Constants.CODE_4007, "签名为空！", null);
    }
    public static Result successSignJudge(){
        return new Result(Constants.CODE_2003, "注册鉴权已完成！", null);
    }
    public static Result errorGetAppSecret(){return new Result(Constants.CODE_4003, "访问是被禁止的", null);}

    public static Result error(String code, String msg) {
        return new Result(code, msg, null);
    }

    public static Result error() {
        return new Result(Constants.CODE_5000, "系统错误", null);
    }

    public static Result urlIsExist(Object data) {
        return new Result(Constants.CODE_6000, "该图片url已存在", data);
    }

}
