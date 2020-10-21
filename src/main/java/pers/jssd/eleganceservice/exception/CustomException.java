package pers.jssd.eleganceservice.exception;

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
     * 异常信息
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
