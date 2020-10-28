package pers.jssd.eleganceservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.jssd.eleganceservice.pojo.StudentDo;

/**
 * @author jssdjing@gmail.com
 */
public interface StudentRepo extends JpaRepository<StudentDo, Long> {

    /**
     * 通过名字查询学生信息
     *
     * @param name 姓名
     * @return 返回查询到的学生信息
     */
    StudentDo findByName(String name);

}
