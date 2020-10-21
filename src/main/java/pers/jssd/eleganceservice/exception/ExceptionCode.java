package pers.jssd.eleganceservice.exception;

/**
 * 自定义异常状态码
 *
 * @author jssdjing@gmail.com
 */
public enum ExceptionCode {

    /**
     * 用户输入异常
     */
    USER_INPUT_ERROR(400, "您输入的数据错误或您没有权限访问资源！"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR(500, "系统出现异常，请您稍后再试或联系管理员！"),

    /**
     * 未知异常
     */
    OTHER_ERROR(503, "系统出现未知异常，请联系管理员！");

    ExceptionCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 异常类型中文描述
     */
    private final String desc;

    /**
     * code
     */
    private final int code;

    public String getDesc() {
        return desc;
    }

    public int getCode() {
        return code;
    }

}
