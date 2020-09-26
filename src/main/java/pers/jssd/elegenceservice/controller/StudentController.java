package pers.jssd.elegenceservice.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.jssd.elegenceservice.entity.AjaxResponse;
import pers.jssd.elegenceservice.entity.PageBean;
import pers.jssd.elegenceservice.pojo.Student;
import pers.jssd.elegenceservice.service.StudentService;

import javax.validation.constraints.PositiveOrZero;

/**
 * 学生类控制器
 *
 * @author jssdjing@gmail.com
 */
@Validated
@RestController
@RequestMapping("/api/v1/elegance/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {this.studentService = studentService;}

    @GetMapping
    public AjaxResponse findAll(@RequestParam(defaultValue = "0", required = false) int page,
                                @RequestParam(defaultValue = "5", required = false) int size) {
        PageBean<Student> pageBean = studentService.findAll(page, size);
        return AjaxResponse.success(pageBean);
    }

    @GetMapping("/{id}")
    public AjaxResponse findOneById(@PositiveOrZero(message = "id格式错误-请输入正整数类型id") @PathVariable long id) {
        Student student = studentService.findOneById(id);
        if (student == null) {
            return AjaxResponse.success(null, "无此类型数据");
        }
        return AjaxResponse.success(student);
    }

    @PostMapping
    public AjaxResponse addOne(@RequestBody Student student) {
        return AjaxResponse.success();
    }
}