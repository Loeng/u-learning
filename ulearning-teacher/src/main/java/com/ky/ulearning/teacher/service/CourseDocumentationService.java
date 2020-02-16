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
}
