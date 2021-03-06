package com.ky.ulearning.student.dao;

import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.vo.KeyLabelVo;
import com.ky.ulearning.spi.system.dto.TeachingTaskDto;
import com.ky.ulearning.spi.system.entity.TeachingTaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 教学任务dao
 *
 * @author luyuhao
 * @since 20/02/21 22:33
 */
@Mapper
@Repository
public interface TeachingTaskDao {

    /**
     * 分页查询未选教学任务信息
     *
     * @param teachingTaskDto 筛选参数
     * @param pageParam       分页参数
     * @return 教学任务信息集合
     */
    List<TeachingTaskEntity> listNotSelectedPage(@Param("teachingTaskDto") TeachingTaskDto teachingTaskDto, @Param("pageParam") PageParam pageParam);

    /**
     * 计算分页查询未选总记录数
     *
     * @param teachingTaskDto 筛选条件
     * @return 总记录数
     */
    Integer countNotSelectedListPage(@Param("teachingTaskDto") TeachingTaskDto teachingTaskDto);

    /**
     * 分页查询已选教学任务信息
     *
     * @param teachingTaskDto 筛选参数
     * @param pageParam       分页参数
     * @return 教学任务信息集合
     */
    List<TeachingTaskEntity> listSelectedPage(@Param("teachingTaskDto") TeachingTaskDto teachingTaskDto, @Param("pageParam") PageParam pageParam);

    /**
     * 计算分页查询已选总记录数
     *
     * @param teachingTaskDto 筛选条件
     * @return 总记录数
     */
    Integer countSelectedListPage(@Param("teachingTaskDto") TeachingTaskDto teachingTaskDto);

    /**
     * 查询所有教学任务信息
     *
     * @return 教学任务id-教学任务名
     */
    List<KeyLabelVo> getAllTeachingTaskArray();

    /**
     * 根据教学任务id查询课程id
     *
     * @param id 教学任务id
     * @return 课程id
     */
    Long getCourseIdById(Long id);

    /**
     * 根据id查询教学任务dto对象
     *
     * @param id 教学任务id
     * @return 教学任务dto对象
     */
    TeachingTaskDto getDtoById(Long id);

    /**
     * 根据id查询教学任务对象
     *
     * @param id 教学任务id
     * @return 教学任务对象
     */
    TeachingTaskEntity getById(Long id);
}
