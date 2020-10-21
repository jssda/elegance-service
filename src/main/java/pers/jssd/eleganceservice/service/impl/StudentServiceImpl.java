package pers.jssd.eleganceservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.jssd.eleganceservice.entity.PageBean;
import pers.jssd.eleganceservice.pojo.StudentDo;
import pers.jssd.eleganceservice.pojo.StudentVo;
import pers.jssd.eleganceservice.repo.StudentRepo;
import pers.jssd.eleganceservice.service.StudentService;
import pers.jssd.eleganceservice.utils.PageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
@Service
@Slf4j
@Transactional(rollbackFor = Error.class)
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;

    public StudentServiceImpl(StudentRepo studentRepo) {this.studentRepo = studentRepo;}

    @Override
    public PageBean<StudentDo> findAll(int page, int size) {
        Page<StudentDo> all = studentRepo.findAll(PageUtils.getPageRequest(page, size));
        List<StudentVo> studentVoList = new ArrayList<>();
        for (StudentDo studentDo : all.getContent()) {
            StudentVo studentVo = new StudentVo();
            BeanUtils.copyProperties(studentDo, studentVo);
            studentVoList.add(studentVo);
        }
        log.info("studentVoList = " + studentVoList);
        return PageBean.getPageBean(all.getContent(), all.getTotalPages(), all.getTotalElements(), page, size);
    }

    @Override
    public StudentVo findOneById(long id) {
        StudentDo studentDo = studentRepo.findById(id).orElse(null);
        StudentVo studentVo = new StudentVo();
        if (studentDo == null) {
            return null;
        }
        BeanUtils.copyProperties(studentDo, studentVo);
        return studentVo;
    }

    @Override
    public long addOne(StudentDo studentDo) {
        StudentDo save = studentRepo.save(studentDo);
        return save.getId();
    }

    @Override
    public void deleteOne(long id) {
        studentRepo.deleteById(id);
    }

    @Override
    public void updateOne(StudentDo studentDo) {
        studentRepo.save(studentDo);
    }
}
