package pers.jssd.eleganceservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.jssd.eleganceservice.pojo.StudentDo;

/**
 * @author jssdjing@gmail.com
 */
public interface StudentRepo extends JpaRepository<StudentDo, Long> {
}
