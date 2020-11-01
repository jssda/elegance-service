package pers.jssd.eleganceservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pers.jssd.eleganceservice.pojo.CourseDo;
import pers.jssd.eleganceservice.pojo.CourseVo;
import pers.jssd.eleganceservice.pojo.StudentDo;
import pers.jssd.eleganceservice.repo.CourseRepo;
import pers.jssd.eleganceservice.service.CourseService;
import pers.jssd.eleganceservice.service.StudentService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepo courseRepo;
    private final StudentService studentService;

    public CourseServiceImpl(CourseRepo courseRepo, StudentService studentService) {this.courseRepo = courseRepo;
        this.studentService = studentService;
    }

    @Override
    public List<CourseVo> getList() {
        List<CourseDo> all = courseRepo.findAll();
        List<CourseVo> returns = new ArrayList<>();
        for (CourseDo one : all) {
            Long id = one.getId();
            StudentDo oneById = studentService.findOneDoById(id);
            CourseVo courseVo = new CourseVo();
            BeanUtils.copyProperties(one, courseVo);
            courseVo.setStudentDo(oneById);
            returns.add(courseVo);
        }
        return returns;
    }
}
