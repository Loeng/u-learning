package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.teacher.dto.CourseTeachingTaskDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 教学任务dto
 *
 * @author luyuhao
 * @since 20/01/26 16:16
 */
@Mapper
@Repository
public interface TeachingTaskDao {
    /**
     * 分页查询课程&教学任务信息
     *
     * @param pageParam             分页参数
     * @param courseTeachingTaskDto 筛选条件
     * @return 课程&教学任务集合
     */
    List<CourseTeachingTaskDto> listPage(@Param("courseTeachingTaskDto") CourseTeachingTaskDto courseTeachingTaskDto, @Param("pageParam") PageParam pageParam);

    /**
     * 分页查询课程&教学任务信息-总记录数
     *
     * @param courseTeachingTaskDto 筛选条件
     * @return 总记录数
     */
    Integer countListPage(@Param("courseTeachingTaskDto") CourseTeachingTaskDto courseTeachingTaskDto);
}