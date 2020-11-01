package pers.jssd.eleganceservice.pojo;

import lombok.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseVo extends CourseDo implements Serializable {

    @Valid
    private StudentDo studentDo;

    @Valid
    private List<StudentDo> studentDos;

    @Override
    public String toString() {
        return super.toString() + "CourseVo{" + "studentDo=" + studentDo + ", studentDos=" + studentDos + '}';
    }
}


