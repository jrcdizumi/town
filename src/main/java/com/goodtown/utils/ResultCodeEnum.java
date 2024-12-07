package com.goodtown.utils;

public enum ResultCodeEnum {
    SUCCESS(200,"success"),
    USERNAME_ERROR(501,"Username does not exist"),
    PASSWORD_ERROR(503,"Password wrong"),
    NOTLOGIN(504,"Not logged in"),
    USERNAME_USED(505,"Username is already in use"),
    PASSWORD_TOO_SHORT(506,"Password is too short"),
    PASSWORD_TOO_FEW_DIGITS(507,"Password must contain at least two digits"),
    PASSWORD_CASE_REQUIREMENT(508,"password cannot be all uppercase or all lowercase"),;

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
