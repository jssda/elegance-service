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

使用校验框架校验一个Controller请求的参数非常简单

当 Controller 需要校验一些基本的参数的时候，使用对应的注解。

```java
/**
 * 通过审核的id, 查询审核的详细信息
 *
 * @param approvalId 审核eid
 * @return 返回响应到前端的信息
 */
@PostMapping("directory/findApplicationById")
public Result findAuditById(@NotBlank(message = "审核id不可为空") String approvalId,
                            @NotBlank(message = "用户did不可为空") String userDid) {
    return Result.success(dirAuditService.findAuditById(approvalId, userDid));
}
```

仅仅使用校验注解还不行，在 Controller 类的头部使用注解 `@Validated` 启动校验。







# 优雅的异常捕获与处理







# 资料参考

https://blog.csdn.net/yuanlaijike/article/details/83017609

