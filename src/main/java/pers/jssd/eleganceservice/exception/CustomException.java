package pers.jssd.eleganceservice.exception;

import pers.jssd.eleganceservice.utils.ExceptionUtils;

/**
 * 自定义异常
 *
 * @author jssdjing@gmail.com
 */
public class CustomException extends RuntimeException {

    /**
     * 异常错误编码
     */
    private int code;

    /**
     * 异常堆栈信息
     */
    private String message;

    /**
     * 响应到前端的异常提示信息
     */
    private String info;


    private CustomException() {}

    public CustomException(ExceptionCode exceptionTypeEnum) {
        this.code = exceptionTypeEnum.getCode();
        this.message = exceptionTypeEnum.getDesc();
    }

    public CustomException(ExceptionCode exceptionCode, String message) {
        this.code = exceptionCode.getCode();
        this.message = message;
    }

    public CustomException(ExceptionCode exceptionCode, String message, String info) {
        this(exceptionCode, message);
        this.info = info;
    }

    public static CustomException systemException(Exception e) {
        return new CustomException(ExceptionCode.SYSTEM_ERROR, ExceptionUtils.getStackTraceInfo(e),
                ExceptionCode.SYSTEM_ERROR.getDesc());
    }

    public static CustomException systemException(Exception e, String info) {
        return new CustomException(ExceptionCode.SYSTEM_ERROR, "111", info);
    }

    public static CustomException userException(Exception e, String info) {
        return new CustomException(ExceptionCode.USER_INPUT_ERROR, ExceptionUtils.getStackTraceInfo(e), info);
    }

    public static CustomException userException(Exception e) {
        return new CustomException(ExceptionCode.USER_INPUT_ERROR, ExceptionUtils.getStackTraceInfo(e),
                ExceptionCode.USER_INPUT_ERROR.getDesc());
    }

    public static CustomException userException(String message, String info) {
        return new CustomException(ExceptionCode.USER_INPUT_ERROR, message, info);
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getInfo() {
        return info;
    }

}
