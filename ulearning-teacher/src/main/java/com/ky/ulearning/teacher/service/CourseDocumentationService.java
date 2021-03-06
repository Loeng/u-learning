package com.ky.ulearning.teacher.service;

import com.ky.ulearning.spi.teacher.dto.CourseDocumentationDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileDocumentationDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileDto;

import java.util.List;

/**
 * 文件资料service - 接口类
 *
 * @author luyuhao
 * @since 20/02/14 20:31
 */
public interface CourseDocumentationService {

    /**
     * 保存文件资料信息
     *
     * @param courseDocumentationDto 文件资料信息
     * @param courseFileDto          课程文件信息
     */
    void save(CourseDocumentationDto courseDocumentationDto, CourseFileDto courseFileDto);

    /**
     * 查询文件资料集合
     *
     * @param courseFileDocumentationDto 筛选对象
     * @return 返回课程文件资料集合
     */
    List<CourseFileDocumentationDto> getList(CourseFileDocumentationDto courseFileDocumentationDto);

    /**
     * 根据文件id查询课程文件资料对象
     *
     * @param fileId 文件id
     * @return 课程文件资料对象
     */
    CourseFileDocumentationDto getByFileId(Long fileId);

    /**
     * 根据id查询课程文件资料对象
     *
     * @param id 文件资料id
     * @return 课程文件资料对象
     */
    CourseFileDocumentationDto getById(Long id);

    /**
     * 更新课程文件资料
     *
     * @param courseFileDocumentationDto 课程文件资料对象
     */
    void update(CourseFileDocumentationDto courseFileDocumentationDto);

    /**
     * 根据id和fileId删除课程文件资料
     *
     * @param id       文件资料id
     * @param fileId   课程文件id
     * @param updateBy 更新者
     */
    void delete(Long id, Long fileId, String updateBy);

    /**
     * 根据父节点id查询所有课程文件资料
     *
     * @param fileParentId 父节点id
     * @return 课程文件资料
     */
    List<CourseFileDocumentationDto> getListByFileParentId(Long fileParentId);

    /**
     * 根据课程id和工号查询id
     *
     * @param courseId       课程id
     * @param username       工号
     * @param teachingTaskId 教学任务id
     * @return id
     */
    CourseFileDocumentationDto getByCourseIdAndUsername(Long courseId, String username, Long teachingTaskId);

    /**
     * 根据父节点id和文件类型查询所有课程文件资料
     *
     * @param fileParentId 父节点id
     * @param fileType     文件类型
     * @return 课程文件资料
     */
    List<CourseFileDocumentationDto> getListByFileParentIdAndFileType(Long fileParentId, Integer fileType);

    /**
     * 分享课程文件资料
     *
     * @param id                  id
     * @param documentationShared 是否共享
     * @param updateBy            更新者
     */
    void updateShared(Long id, Boolean documentationShared, String updateBy);

    /**
     * 根据课程id查询分享区根节点
     *
     * @param courseId 课程id
     * @return 文件资料根节点对象
     */
    Long getSharedByCourseId(Long courseId);

    /**
     * 查询共享区文件资料列表
     *
     * @param courseFileDocumentationDto 查询条件
     * @return 文件资料列表
     */
    List<CourseFileDocumentationDto> getSharedList(CourseFileDocumentationDto courseFileDocumentationDto);
}
