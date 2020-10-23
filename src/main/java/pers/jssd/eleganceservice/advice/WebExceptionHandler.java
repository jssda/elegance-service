package pers.jssd.eleganceservice.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.jssd.eleganceservice.entity.AjaxResponse;
import pers.jssd.eleganceservice.exception.CustomException;
import pers.jssd.eleganceservice.exception.ExceptionCode;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异常处理
 *
 * @author jssd
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class WebExceptionHandler {

    /**
     * 处理程序员主动转换的自定义异常
     */
    @ExceptionHandler(CustomException.class)
    public AjaxResponse customerException(CustomException e) {
        if (e.getCode() == ExceptionCode.SYSTEM_ERROR.getCode() || e.getCode() == ExceptionCode.OTHER_ERROR.getCode()) {
            // 输出到日志框架, 持久化处理
            log.error(e.getInfo(), e);
        }
        return AjaxResponse.error(e);
    }

    /**
     * 处理绑定异常.
     * <pre>
     * 出现场景: bean中有字段验证, Validated Valid 注解指定要验证这个bean对象.
     *      当前端传过来一个表单格式(Content-Type: multipart/form-data)的数据, 后台通过需要验证的bean对象接收的时候.
     *      加入验证不通过, 则会报此异常
     * </pre>
     */
    @ExceptionHandler({BindException.class})
    protected AjaxResponse handleBindException(BindException ex) {
        List<Map<String, String>> list = new ArrayList<>();
        for (ObjectError objectError : ex.getAllErrors()) {
            resolveError(list, objectError);
        }
        log.error("参数绑定错误", ex);
        return AjaxResponse.error(ExceptionCode.USER_INPUT_ERROR, "参数绑定错误", list);
    }

    /**
     * 请求体绑定异常
     * <pre>
     *  与BindException类似, 不同的是因为什么触发, 当Controller接收的是一个json格式, @RequestBody接收参数时,
     *      验证失败会抛出此异常
     * </pre>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected AjaxResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("请求体绑定失败", exception);
        List<Map<String, String>> list = new ArrayList<>();
        for (ObjectError objectError : exception.getBindingResult().getAllErrors()) {
            resolveError(list, objectError);
        }
        return AjaxResponse.error(ExceptionCode.USER_INPUT_ERROR, "请求体绑定失败", list);
    }

    /**
     * 触发场景
     * Controller中的参数校验失败会抛出此类异常. 类头部需要添加@Valited注解
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public AjaxResponse handleConstraintViolationException(ConstraintViolationException e) {
        log.error("输入参数异常", e);
        List<Map<String, String>> list = new ArrayList<>();
        // e.getMessage() 的格式为 getUser.id: id不能为空, getUser.name: name不能为空
        String[] messages = e.getMessage().split(", ");
        for (String msg : messages) {
            String[] fieldAndMsg = msg.split(": ");
            String field = fieldAndMsg[0].split("\\.")[1];
            String message = fieldAndMsg[1];

            Map<String, String> map = new HashMap<>(1);
            map.put("field", field);
            map.put("message", message);
            list.add(map);
        }
        return AjaxResponse.error(ExceptionCode.USER_INPUT_ERROR, "输入参数异常", list);
    }

    /**
     * spring 断言触发的异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public AjaxResponse handleIllegalArgumentException(IllegalArgumentException e) {
        return AjaxResponse.error(new CustomException(ExceptionCode.USER_INPUT_ERROR, e.getMessage()));
    }

    /**
     * 处理程序员在程序中未能捕获（遗漏的）异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResponse exception(Exception e) {
        log.error(e.getMessage(), e);
        return AjaxResponse.error(new CustomException(ExceptionCode.OTHER_ERROR, e.getMessage(), "系统异常"));
    }

    /**
     * 解析参数结果, 存储到list中
     *
     * @param list        存储错误结果的list
     * @param objectError 错误
     */
    private void resolveError(List<Map<String, String>> list, ObjectError objectError) {
        Map<String, String> map = new HashMap<>(1);
        if (objectError instanceof FieldError) {
            FieldError fieldError = (FieldError) objectError;
            map.put("field", fieldError.getField());
            map.put("message", fieldError.getDefaultMessage());
        } else {
            map.put("field", objectError.getObjectName());
            map.put("message", objectError.getDefaultMessage());
        }
        list.add(map);
    }


}