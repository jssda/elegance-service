package pers.jssd.elegenceservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.jssd.elegenceservice.entity.PageBean;
import pers.jssd.elegenceservice.pojo.Student;
import pers.jssd.elegenceservice.repo.StudentRepo;
import pers.jssd.elegenceservice.service.StudentService;
import pers.jssd.elegenceservice.utils.PageUtils;

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
    public PageBean<Student> findAll(int page, int size) {
        Page<Student> all = studentRepo.findAll(PageUtils.getPageRequest(page, size));

        return PageBean.getPageBean(all.getContent(), all.getTotalPages(), all.getTotalElements(), page, size);
    }

    @Override
    public Student findOneById(long id) {
        return studentRepo.findById(id).orElse(null);
    }
}
