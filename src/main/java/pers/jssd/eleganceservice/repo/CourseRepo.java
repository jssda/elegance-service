package pers.jssd.eleganceservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.jssd.eleganceservice.pojo.CourseDo;

/**
 * @author jssdjing@gmail.com
 */
public interface CourseRepo  extends JpaRepository<CourseDo, Long> {
}
