package pers.jssd.eleganceservice.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.jssd.eleganceservice.entity.AjaxResponse;
import pers.jssd.eleganceservice.entity.Insert;
import pers.jssd.eleganceservice.pojo.CourseVo;
import pers.jssd.eleganceservice.service.CourseService;

import java.util.List;

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

    private final CourseService courseService;

    public CourseController(CourseService courseService) {this.courseService = courseService;}

    @PostMapping
    public AjaxResponse testNested(@RequestBody @Validated(Insert.class) CourseVo courseVo) {
        System.out.println("courseVo = " + courseVo);
        return AjaxResponse.success();
    }

    @GetMapping
    public AjaxResponse getCourses() {
        List<CourseVo> lists = courseService.getList();
        return AjaxResponse.success(lists);
    }

}
