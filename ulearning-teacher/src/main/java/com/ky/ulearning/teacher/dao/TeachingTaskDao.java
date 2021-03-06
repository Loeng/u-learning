package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.TeachingTaskDto;
import com.ky.ulearning.spi.system.entity.TeachingTaskEntity;
import com.ky.ulearning.spi.teacher.dto.CourseTeachingTaskDto;
import com.ky.ulearning.spi.teacher.vo.TeachingTaskVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

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

    /**
     * 根据教师id、课程id、学期和别称查询教学任务信息
     *
     * @param teachingTaskDto 查询条件参数对象
     * @return 教学任务对象
     */
    TeachingTaskEntity getByTeaIdAndCourseIdAndTermAndAlias(TeachingTaskDto teachingTaskDto);

    /**
     * 插入教学任务信息
     *
     * @param teachingTaskDto 教学任务信息
     */
    void insert(TeachingTaskDto teachingTaskDto);

    /**
     * 更新教学任务信息
     *
     * @param teachingTaskDto 教学任务对象
     */
    void update(TeachingTaskDto teachingTaskDto);

    /**
     * 根据教师id查询所有教学任务id集合
     *
     * @param teaId 教师id
     * @return 教学任务id集合
     */
    Set<Long> getIdByTeaId(Long teaId);

    /**
     * 根据教学任务id查询课程教学任务信息
     *
     * @param id 教学任务id
     * @return 返回课程教学任务对象
     */
    CourseTeachingTaskDto getById(Long id);

    /**
     * 根据教师id查询所有课程id集合
     *
     * @param teaId 教师id
     * @return 课程id集合
     */
    Set<Long> getCourseIdByTeaId(Long teaId);

    /**
     * 获取教师所有教学任务信息
     *
     * @param username 教师工号
     * @return 返回所有教学任务信息
     */
    List<TeachingTaskVo> getAll(String username);

    /**
     * 根据id查询课程id
     *
     * @param id 教学任务id
     * @return 课程id
     */
    Long getCourseIdById(Long id);

    /**
     * 根据id查询教学任务信息
     *
     * @param id 教学任务id
     * @return 教学任务信息
     */
    TeachingTaskDto getInfoById(Long id);

    /**
     * 根据课程id和term查询所有教学任务
     *
     * @param courseId 课程id
     * @param term     学期
     * @return 教学任务集合
     */
    List<TeachingTaskEntity> getByCourseIdAndTerm(@Param("courseId") Long courseId, @Param("term") String term);

    /**
     * 查询所属相同课程的历年教学任务信息
     *
     * @param courseId 课程id
     * @param term     学期
     * @param username 教师工号
     * @param id       教学任务id
     * @return 教学任务信息
     */
    List<TeachingTaskVo> getBeforeTeachingTask(@Param("courseId") Long courseId, @Param("term") String term, @Param("username") String username, @Param("id") Long id);
}
