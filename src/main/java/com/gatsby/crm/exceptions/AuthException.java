package com.gatsby.crm.exceptions;

/**
 * @PACKAGE_NAME: com.gatsby.crm.exceptions
 * @NAME: AuthException
 * @AUTHOR: Jonah
 * @DATE: 2023/6/10
 */
public class AuthException extends RuntimeException {
    private Integer code = 400;
    private String msg = "暂无权限!";


    public AuthException() {
        super("暂无权限!");
    }

    public AuthException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public AuthException(Integer code) {
        super("暂无权限!");
        this.code = code;
    }

    public AuthException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
