package com.goodtown.utils;

public enum ResultCodeEnum {
    SUCCESS(200,"success"),
    USERNAME_ERROR(501,"用户不存在"),
    PASSWORD_ERROR(503,"密码错误"),
    NOTLOGIN(504,"未登录"),
    USERNAME_USED(505,"用户名已存在"),
    PASSWORD_TOO_SHORT(506,"密码太短（不少于 6 位）"),
    PASSWORD_TOO_FEW_DIGITS(507,"密码太简单（包含至少两个数字）"),
    PASSWORD_CASE_REQUIREMENT(508,"密码太简单（包含字母且不能全为大写或小写）"),;

    private Integer code;
    private String message;
    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
