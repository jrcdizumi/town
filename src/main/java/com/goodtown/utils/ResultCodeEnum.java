package com.goodtown.utils;

public enum ResultCodeEnum {
    SUCCESS(200,"success"),
    USERNAME_ERROR(501,"usernameError"),
    PASSWORD_ERROR(503,"passwordError"),
    NOTLOGIN(504,"notLogin"),
    USERNAME_USED(505,"userNameUsed"),
    PASSWORD_TOO_SHORT(506,"passwordTooShort"),
    PASSWORD_TOO_FEW_DIGITS(507,"passwordTooFewDigits"),
    PASSWORD_CASE_REQUIREMENT(508,"passwordCaseRequirement");

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
