package pers.jssd.elegenceservice.service;

import pers.jssd.elegenceservice.entity.PageBean;
import pers.jssd.elegenceservice.pojo.Student;

/**
 * 学生业务接口
 *
 * @author jssdjing@gmail.com
 */
public interface StudentService {
    /**
     * 分页查询全部学生的方法
     *
     * @param page 当前页, 从零开始
     * @param size 每页多少条数据
     * @return 返回查询到的分页数据
     */
    PageBean<Student> findAll(int page, int size);

    /**
     * 通过id查询一条数据
     *
     * @param id 查询的学生id
     * @return 返回查询到的学生
     */
    Student findOneById(long id);
}
