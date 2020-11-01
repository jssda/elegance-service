[toc]

# elegance-service

此项目为Spring中的校验框架学习。



# SpringBoot校验框架介绍

## 前言:

前后端分离的开发模式越来越成为主流开发模式。前端可以使用JS进行数据的校验。但是对于后台来说，不能全靠前端校验。我们需要防止绕过前端的请求，所以在后端也需要校验。同时，后端业务编写的时候，怎么能让代码更优雅呢，我想对于任何人来说，都不希望总是大段大段的`if-else`判断语句来校验数据吧。

[JSR-303](https://link.jianshu.com/?t=https://jcp.org/en/jsr/detail?id=303) 是 Java EE 6 中的一项子规范，叫做 Bean Validation。[JSR-349](https://jcp.org/en/jsr/detail?id=349)是其的升级版本，添加了一些新特性，他们规定一些校验规范即校验注解，如`@Null`，`@NotNull`，`@Pattern`，他们位于`javax.validation.constraints`包下，只提供规范不提供实现。

Spring Boot在内部通过集成hibernate-validation已经实现了JSR-349验证规范接口，在Spring Boot项目中只要直接使用就行了。不仅如此，SpringBoot为了方便开发者，对Hibernate Validation进行了二次封装。显示校验validated bean时，你可以使用`Spring Validation`或者`Hibernate Validation`，而`Spring Validation`另一个特性，便是**其在SpringMVC模块中添加了自动校验，并将校验信息封装进了特定的类中**。这无疑便捷了我们的Web开发。

## 环境

开发环境：idea2020.2

java版本：jdk.1.8

SpringBoot版本：2.3.4.RELEASE

数据库：阿里云数据库实验室 mysql8

## 依赖

引入SpringBoot依赖框架的启动器，即可使用对应的校验注解。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

## 使用

### 校验注解

当引入了校验的注解之后, 假如你使用的是 idea 开发工具的话, 在主展示页面的右边, 有一个`Bean Validatin`的选项卡, 点开选显卡, 可以看到`Constraints`导航, 点开就能看到校验的所有注解.

<img src="https://raw.githubusercontent.com/jssda/picbed/master/image-20201101170320182.png" alt="image-20201101170320182" style="zoom:50%;" />

### 普通参数校验

使用校验框架校验一个Controller请求的参数非常简单

当 Controller 需要校验一些基本的参数的时候，使用对应的注解。

```java
@RestController
@Validated
@RequestMapping("/api/v2/elegance/students")
public class VerifyDemoController {

    private final StudentService studentService;

    public VerifyDemoController(StudentService studentService) {this.studentService = studentService;}

    @GetMapping
    public AjaxResponse findStudentsByUserName(
            @NotBlank(message = "查询的姓名不能为空") @RequestParam(name = "name") String name) {

        StudentVo studentVo = studentService.findStudentsByUserName(name);
        return AjaxResponse.success(studentVo);
    }
}
```

仅仅使用校验注解还不行，在 Controller 类的头部使用注解 `@Validated` 启动校验。

 

那么，我们访问一个错误的 url ：`GET http://localhost:8080/api/v2/elegance/students?name=`

返回信息

```json
{"code":400,"message":"输入参数异常","data":[{"field":"name","message":"查询的姓名不能为空"}]}
```

错误日志

```log
2020-10-28 19:52:35.756 ERROR 11656 --- [nio-8080-exec-7] p.j.e.advice.WebExceptionHandler         : 输入参数异常

javax.validation.ConstraintViolationException: findStudentsByUserName.name: 查询的姓名不能为空
	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:116) ~[spring-context-5.2.9.RELEASE.jar:5.2.9.RELEASE]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.9.RELEASE.jar:5.2.9.RELEASE]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.9.RELEASE.jar:5.2.9.RELEASE]
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:691) ~[spring-aop-5.2.9.RELEASE.jar:5.2.9.RELEASE]
	at pers.jssd.eleganceservice.controller.VerifyDemoController$$EnhancerBySpringCGLIB$$ef60bb9f.findStudentsByUserName(<generated>) ~[classes/:na]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_231]
...
```

返回一个 `javax.validation.ConstraintViolationException` 异常。

### 实体参数校验

假如 Controller 接收的是一个实体参数, 那么实体的字段怎么校验呢? 也很简单, 在你的实体类对应的字段添加注解即可. 

```java
package pers.jssd.eleganceservice.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.jssd.eleganceservice.entity.Insert;
import pers.jssd.eleganceservice.entity.Update;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @author jssdjing@gmail.com
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class StudentDo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "不能缺少id字段", groups = Update.class)
    @Null(message = "此字段需为空", groups = Insert.class)
    private Long id;

    @NotBlank(message = "学生姓名不可为空")
    private String name;

    @Min(message = "成绩不可低于0分", value = 0)
    @Max(message = "成绩不可高于100分", value = 100)
    private Integer grade;

    @NotNull(message = "课程id不可为空")
    @Column(name = "class")
    private Integer clazz;
}

```

如上代码. 其中 `@Data`、`@Builder`、`@NoArgsConstructor`、`@AllArgsConstructor`、`@Entity`、`@Table(name = "students")`、`@Id`、`@GeneratedValue(strategy = GenerationType.IDENTITY)`、`@Column(name = "class")`这些注解均为 Spring Boot Jpa 当中的注解，与校验无关。

```java
/**
* 添加一个学生信息
*
* @param studentDo 学生信息
* @return AjaxResponse
*/
@PostMapping
public AjaxResponse addOne(@Validated(Insert.class) @RequestBody StudentDo studentDo) {
    long id = studentService.addOne(studentDo);
    return AjaxResponse.success(id);
}
```

注意，别忘了在Controller类之上添加一个 `@Validated` 注解来启动校验。

### 分组校验

实体校验和参数校验已经讲完了，还是很简单的。但是在开发的时候， 难免会遇到各种各样的需求。一个很简单的需求就是，我添加的时候，接收到的实体类不需要`id`字段的值, 但是我更新的时候, 接收的实体类参数需要有 id 这个字段的值, 并且我还要校验他. 怎么办? 难道我写两个实体类分别在添加和更新的时候用吗? 大可不必, 这个时候我们可以用分组校验. 标识在什么时候使用这个校验注解.

```java
@NotNull(message = "不能缺少id字段", groups = Update.class)
@Null(message = "此字段需为空", groups = Insert.class)
private Long id;
```

可以看到, 注解有一个 groups 的参数, 这个参数标识这个注解在指定的分组可用. 在 Controller 请求的时候, 指定需要使用那个分组即可.

```java
@PostMapping
public AjaxResponse addOne(@Validated(Insert.class) @RequestBody StudentDo studentDo) {
    long id = studentService.addOne(studentDo);
    return AjaxResponse.success(id);
}
```

这里就是指定我要使用 Insert 分组.

### 嵌套校验

很多时候, 一个实体类当中会包含其他的实体, 这个时候可以嵌套校验吗? 是可以的, 虽然这个东西不常用. 但是还要说一下.

使用`@Valid`注解可以嵌套校验实体参数

```java
package pers.jssd.eleganceservice.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author jssdjing@gmail.com
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class CourseDo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Max(20)
    private Long id;

    @NotBlank
    private String name;
}
```

```java
package pers.jssd.eleganceservice.pojo;

import lombok.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseVo extends CourseDo implements Serializable {

    @Valid
    private StudentDo studentDo;

    @Valid
    private List<StudentDo> studentDos;

    @Override
    public String toString() {
        return super.toString() + "CourseVo{" + "studentDo=" + studentDo + ", studentDos=" + studentDos + '}';
    }
}
```

### Spring 断言校验

如果你看过Spring源码的话, Spring对参数的校验很优雅. 这里我们可以用Spring的断言更好的处理我们的校验.

比如判断一个对象是否错误

```
Assert.hasText((message, "输入信息错误!");
```

如果失败会抛出一个`IllegalArgumentException`

# 优雅的异常捕获与处理

校验是有了, 但是怎么友好的通知用户呢? 我们使用切面直接捕获所有的异常, 通知用户.

```java
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
```

# 资料参考

https://blog.csdn.net/yuanlaijike/article/details/83017609

https://blog.csdn.net/qq_33376750/article/details/65440436