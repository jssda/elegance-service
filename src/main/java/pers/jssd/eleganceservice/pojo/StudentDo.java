package pers.jssd.eleganceservice.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.jssd.eleganceservice.entity.Insert;
import pers.jssd.eleganceservice.entity.Update;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @author jssdjing@gmail.com
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class StudentDo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "不能缺少id字段", groups = Update.class)
    @Null(message = "此字段需为空", groups = Insert.class)
    private Long id;

    @NotBlank(message = "学生姓名不可为空")
    private String name;

    @Min(message = "成绩不可低于0分", value = 0)
    @Max(message = "成绩不可高于100分", value = 100)
    private Integer grade;

    @NotNull(message = "课程id不可为空")
    @Column(name = "class")
    private Integer clazz;
}
