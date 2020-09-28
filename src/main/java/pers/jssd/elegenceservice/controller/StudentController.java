package pers.jssd.elegenceservice.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.jssd.elegenceservice.entity.AjaxResponse;
import pers.jssd.elegenceservice.entity.Insert;
import pers.jssd.elegenceservice.entity.PageBean;
import pers.jssd.elegenceservice.pojo.StudentDo;
import pers.jssd.elegenceservice.pojo.StudentVo;
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

    /**
     * 分页查询所有学生
     *
     * @param page 当前页
     * @param size 每页多少条数据
     * @return AjaxResponse
     */
    @GetMapping
    public AjaxResponse findAll(@RequestParam(defaultValue = "0", required = false) int page,
                                @RequestParam(defaultValue = "5", required = false) int size) {
        PageBean<StudentDo> pageBean = studentService.findAll(page, size);
        return AjaxResponse.success(pageBean);
    }

    /**
     * 查询一个学生的详情
     *
     * @param id 学生id
     * @return AjaxResponse
     */
    @GetMapping("/{id}")
    public AjaxResponse findOneById(@PositiveOrZero(message = "id格式错误-请输入正整数类型id") @PathVariable long id) {
        StudentVo studentVo = studentService.findOneById(id);
        if (studentVo == null) {
            return AjaxResponse.success(null, "无此类型数据");
        }
        return AjaxResponse.success(studentVo);
    }

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
}
