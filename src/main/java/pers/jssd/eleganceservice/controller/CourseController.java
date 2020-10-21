package pers.jssd.eleganceservice.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.jssd.eleganceservice.entity.AjaxResponse;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

/**
 * 课程请求控制器
 *
 * @author jssd jssdjing@gmail.com
 * @date 2020/9/30 16:51
 */
@Validated
@RestController
@RequestMapping("/api/v1/elegance/course")
public class CourseController {

    @GetMapping
    public AjaxResponse addCourse(@Max(value = 20, message = "id不可大于20") long id, BindingResult idBindingResult,
                                  @NotBlank(message = "名称不可为空") String name, BindingResult bindingResult) {
        System.out.println("idBindingResult = " + idBindingResult);
        System.out.println("bindingResult = " + bindingResult);
        return AjaxResponse.success();
    }

}
