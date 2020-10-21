package pers.jssd.eleganceservice.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.jssd.eleganceservice.entity.Update;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentVo implements Serializable {

    @NotBlank(message = "不能缺少id字段", groups = Update.class)
    private Long id;

    @NotBlank(message = "学生姓名不可为空")
    private String name;

    @Min(message = "成绩不可低于0分", value = 0)
    @Max(message = "成绩不可高于100分", value = 100)
    private Integer grade;

    @NotBlank(message = "课程id不可为空")
    private Integer clazz;

    /**
     * 学生对应的多个课程
     */
    private List<CourseDo> courseDoList;
}
