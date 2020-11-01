package pers.jssd.eleganceservice.service;

import pers.jssd.eleganceservice.pojo.CourseVo;

import java.util.List;

/**
 * @author jssdjing@gmail.com
 */
public interface CourseService {

    /**
     * 查询课表
     *
     * @return 返回查询到的课表信息
     */
    List<CourseVo> getList();

}
