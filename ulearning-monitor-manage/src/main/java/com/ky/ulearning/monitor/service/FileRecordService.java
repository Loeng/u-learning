package com.ky.ulearning.monitor.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.monitor.dto.FileRecordDto;
import com.ky.ulearning.spi.monitor.entity.FileRecordEntity;
import org.springframework.scheduling.annotation.Async;

/**
 * 文件记录service - 接口类
 *
 * @author luyuhao
 * @since 20/02/06 17:04
 */
public interface FileRecordService {

    /**
     * 插入文件记录
     *
     * @param fileRecordDto 待插入的文件记录
     */
    @Async
    void insert(FileRecordDto fileRecordDto);

    /**
     * 分页查询文件记录
     *
     * @param fileRecordDto 筛选参数
     * @param pageParam     分页参数
     * @return 封装文件记录的分页对象
     */
    PageBean<FileRecordEntity> pageFileRecordList(FileRecordDto fileRecordDto, PageParam pageParam);

    /**
     * 根据id查询文件记录
     *
     * @param id 文件记录id
     * @return 文件记录对象
     */
    FileRecordEntity getById(Long id);

    /**
     * 删除文件记录
     *
     * @param id       文件记录id
     * @param updateBy 更新者
     */
    void delete(Long id, String updateBy);
}