package pers.jssd.eleganceservice.service;

import pers.jssd.eleganceservice.entity.PageBean;
import pers.jssd.eleganceservice.pojo.StudentDo;
import pers.jssd.eleganceservice.pojo.StudentVo;

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
    PageBean<StudentDo> findAll(int page, int size);

    /**
     * 通过id查询一条数据
     *
     * @param id 查询的学生id
     * @return 返回查询到的学生
     */
    StudentVo findOneById(long id);

    /**
     * 添加一个学生信息
     *
     * @param studentDo 添加的学生Do对象
     * @return 返回添加成功之后的id
     */
    long addOne(StudentDo studentDo);

    /**
     * 删除一个学生信息
     *
     * @param id 要删除的学生id
     */
    void deleteOne(long id);

    /**
     * 更新一个学生信息
     * @param studentDo 学生实体类
     */
    void updateOne(StudentDo studentDo);

    /**
     * 通过用户姓名查询用户
     *
     * @param name 用户姓名
     * @return 返回查询到的用户
     */
    StudentVo findStudentsByUserName(String name);
}
