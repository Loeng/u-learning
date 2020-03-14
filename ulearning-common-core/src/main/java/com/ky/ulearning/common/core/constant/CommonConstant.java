package com.ky.ulearning.common.core.constant;

import io.swagger.models.auth.In;

import java.util.Map;

/**
 * 业务通用常量
 *
 * @author luyuhao
 * @since 2020/03/12 23:17
 */
public class CommonConstant {

    /**
     * 测试任务状态，1：未发布 2：未开始 3：进行中 4：已结束
     */
    public static final Integer[] EXAMINATION_STATE = {1, 2, 3, 4};

    public static final String COURSE_QUESTION_SEPARATE = "\\|#\\|";

    public static final String DRUID_STAT_WEB_URI = "/weburi.json";

}