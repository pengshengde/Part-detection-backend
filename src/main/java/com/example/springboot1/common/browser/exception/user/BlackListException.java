package com.example.springboot1.common.browser.exception.user;

/**
 * 黑名单IP异常类
 * 
 * @author ruoyi
 */
public class BlackListException extends UserException
{
    private static final long serialVersionUID = 1L;

    public BlackListException()
    {
        super("login.blocked", null);
    }
}
