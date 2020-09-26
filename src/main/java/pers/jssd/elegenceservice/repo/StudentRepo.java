package pers.jssd.elegenceservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.jssd.elegenceservice.pojo.Student;

/**
 * @author jssdjing@gmail.com
 */
public interface StudentRepo extends JpaRepository<Student, Long> {
}
