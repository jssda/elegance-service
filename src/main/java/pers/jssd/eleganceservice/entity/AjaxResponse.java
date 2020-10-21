package pers.jssd.eleganceservice.entity;

import lombok.Data;
import pers.jssd.eleganceservice.exception.CustomException;
import pers.jssd.eleganceservice.exception.ExceptionCode;

import java.io.Serializable;

/**
 * 前端响应结果类
 *
 * @author jssdjing@gmail.com
 */
@Data
public class AjaxResponse implements Serializable {

    /**
     * 请求响应状态码
     */
    private int code;

    /**
     * 请求结果描述信息
     */
    private String message;

    /**
     * 请求结果数据（通常用于查询操作）
     */
    private Object data;

    private AjaxResponse() {}

    /**
     * 请求出现异常时的响应数据封装
     *
     * @param e 异常信息
     * @return 返回响应
     */
    public static AjaxResponse error(CustomException e) {
        AjaxResponse resultBean = new AjaxResponse();
        resultBean.setCode(e.getCode());
        resultBean.setMessage(e.getInfo());
        return resultBean;
    }


    public static AjaxResponse error(ExceptionCode exceptionCode, String errorMessage, Object data) {
        AjaxResponse resultBean = new AjaxResponse();
        resultBean.setCode(exceptionCode.getCode());
        resultBean.setMessage(errorMessage);
        resultBean.setData(data);
        return resultBean;
    }

    /**
     * 请求出现异常时的响应数据封装
     */
    public static AjaxResponse error(ExceptionCode exceptionCode, String errorMessage) {
        AjaxResponse resultBean = new AjaxResponse();
        resultBean.setCode(exceptionCode.getCode());
        resultBean.setMessage(errorMessage);
        return resultBean;
    }

    /**
     * 请求成功的响应，不带查询数据（用于删除、修改、新增接口）
     */
    public static AjaxResponse success() {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setCode(200);
        ajaxResponse.setMessage("请求响应成功!");
        return ajaxResponse;
    }

    /**
     * 请求成功的响应，带有查询数据（用于数据查询接口）
     */
    public static AjaxResponse success(Object obj) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setCode(200);
        ajaxResponse.setMessage("请求响应成功!");
        ajaxResponse.setData(obj);
        return ajaxResponse;
    }

    /**
     * 请求成功的响应，带有查询数据（用于数据查询接口）
     */
    public static AjaxResponse success(Object obj, String message) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setCode(200);
        ajaxResponse.setMessage(message);
        ajaxResponse.setData(obj);
        return ajaxResponse;
    }


}