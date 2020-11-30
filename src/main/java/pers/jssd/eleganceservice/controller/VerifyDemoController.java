package pers.jssd.eleganceservice.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.jssd.eleganceservice.entity.AjaxResponse;
import pers.jssd.eleganceservice.pojo.StudentVo;
import pers.jssd.eleganceservice.service.StudentService;

import javax.validation.constraints.NotBlank;

/**
 * @author jssd jssdjing@gmail.com
 * @date 2020/10/28 19:10
 */
@RestController
@Validated
@RequestMapping("/api/v2/elegance/students")
public class VerifyDemoController {

    private final StudentService studentService;

    public VerifyDemoController(StudentService studentService) {this.studentService = studentService;}

    @GetMapping
    public AjaxResponse findStudentsByUserName(
            @NotBlank(message = "查询的姓名不能为空111") @RequestParam(name = "name") String name) {

        StudentVo studentVo = studentService.findStudentsByUserName(name);
        return AjaxResponse.success(studentVo);
    }
}
