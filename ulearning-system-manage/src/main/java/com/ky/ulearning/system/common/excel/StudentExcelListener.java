package com.ky.ulearning.system.common.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.ky.ulearning.common.core.utils.JsonUtil;
import com.ky.ulearning.spi.common.excel.StudentExcel;
import com.ky.ulearning.system.auth.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 学生数据导入处理listener
 *
 * @author luyuhao
 * @since 2020/3/23 19:05
 */
@Slf4j
public class StudentExcelListener extends AnalysisEventListener<StudentExcel> {

    /**
     * 每隔n条存储数据库，然后清理list，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    private StudentService studentService;

    private Map<Integer, StudentExcel> map;

    /**
     * 上传失败的list，用于反馈用户数据
     */
    private Map<Integer, StudentExcel> errorMap;

    public StudentExcelListener(StudentService studentService) {
        this.studentService = studentService;
        this.map = new HashMap<>();
        this.errorMap = new HashMap<>();
    }

    /**
     * 这个每一条数据解析都会来调用
     */
    @Override
    public void invoke(StudentExcel data, AnalysisContext context) {
        Integer index = context.readRowHolder().getRowIndex();
        log.info("解析第" + (index + 1) + "条数据:{}", JsonUtil.toJsonString(data));
        map.put(index + 1, data);

        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (map.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            map.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
    }

    private void saveData() {
        //批量保存数据获取存储失败的数据
        Map<Integer, StudentExcel> studentEntityMap = studentService.batchInsertExcel(map);
        if (!CollectionUtils.isEmpty(studentEntityMap)) {
            errorMap.putAll(studentEntityMap);
        }

    }

    public Map<Integer, StudentExcel> getErrorMap() {
        return errorMap;
    }
}
