package com.ky.ulearning.monitor.service;

import cn.hutool.core.date.DateTime;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.monitor.dto.LogDto;
import com.ky.ulearning.spi.monitor.entity.LogEntity;
import com.ky.ulearning.spi.monitor.vo.TrafficOperationVo;
import com.ky.ulearning.spi.monitor.vo.TrafficVo;

import java.util.Date;
import java.util.List;

/**
 * 日志 service 接口类
 *
 * @author luyuhao
 * @date 19/12/05 03:00
 */
public interface LogService {
    /**
     * 插入记录
     *
     * @param logEntity 待插入的日志对象
     */
    void insert(LogEntity logEntity);

    /**
     * 分页查询日志信息
     *
     * @param logDto    日志筛选条件
     * @param pageParam 分页参数
     * @return 返回封装好的分页日志对象
     */
    PageBean<LogEntity> pageLogList(LogDto logDto, PageParam pageParam);

    /**
     * 获取日志类型集合
     *
     * @return 日志类型集合
     */
    List<String> getLogType();

    /**
     * 查询小于等于startDelDate的第一个日期
     *
     * @param dateTime 待比较的时间
     * @return 返回日期
     */
    Date getFirstCreateTimeLessOrEqual(String dateTime);

    /**
     * 根据日期查询日志记录
     *
     * @param date 查询的日期,yyyy-MM-dd
     * @return 返回日志集合
     */
    List<LogEntity> getByDate(String date);

    /**
     * 根据日期删除日志
     *
     * @param date 待删除的日期
     */
    void deleteByDate(String date);

    /**
     * 查询当天的访问量
     *
     * @param today   当天
     * @return 返回当天的访问量
     */
    TrafficVo getTrafficByDate(Date today);

    /**
     * 查询前topNumber条日志
     *
     * @param topNumber 查询数量
     * @return 返回日志对象集合
     */
    List<LogEntity> getLogTop(Integer topNumber);

    /**
     * 查询days范围内的操作数
     *
     * @param days 天数
     * @param username 当前用户
     * @return 返回每天的访问量
     */
    TrafficOperationVo getDaysOperation(Integer days, String username);

    /**
     * 批量插入记录
     *
     * @param logEntityList 待插入的日志对象
     */
    void batchInsert(List<LogEntity> logEntityList);
}
