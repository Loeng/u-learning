package com.ky.ulearning.teacher.common.constants;

import com.ky.ulearning.common.core.exceptions.enums.BaseEnum;
import org.springframework.http.HttpStatus;

/**
 * @author luyuhao
 * @since 20/01/26 21:37
 */
public enum TeacherErrorCodeEnum implements BaseEnum {

    /**
     * 教师端错误状态码
     */
    COURSE_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "课程id不能为空"),
    TEACHING_TASK_ALIAS_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "教学任务别称不能为空"),
    TERM_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "学期不能为空"),
    ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "id不能为空"),
    TEACHING_TASK_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "教学任务id不能为空"),
    STUDENT_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "学生id不能为空"),
    NOTICE_TITLE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "标题不能为空"),
    NOTICE_ATTACHMENT_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "附件不能为空"),
    QUESTION_KNOWLEDGE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "知识模块不能为空"),
    QUESTION_TYPE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "试题类型不能为空"),
    QUESTION_TEXT_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "试题内容不能为空"),
    EXPERIMENT_ATTACHMENT_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "附件不能为空"),
    EXPERIMENT_ORDER_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "实验序号不能为空"),
    EXPERIMENT_TITLE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "实验标题不能为空"),
    EXPERIMENT_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "实验id不能为空"),


    TEA_NUMBER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "教师工号不存在"),
    COURSE_ID_NOT_EXISTS(HttpStatus.BAD_REQUEST, "课程id不存在"),
    NOTICE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "通告不存在"),
    NOTICE_ATTACHMENT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "附件不存在"),
    TEACHING_TASK_NOT_EXISTS(HttpStatus.BAD_REQUEST, "教学任务不存在"),
    COURSE_QUESTION_NOT_EXISTS(HttpStatus.BAD_REQUEST, "试题不存在"),
    EXPERIMENT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "实验不存在"),
    EXPERIMENT_ATTACHMENT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "附件不存在"),

    NOTICE_ATTACHMENT_ILLEGAL(HttpStatus.BAD_REQUEST, "附件已过期"),
    TEACHING_TASK_ID_ILLEGAL(HttpStatus.BAD_REQUEST, "该教学任务不可操作"),
    STUDENT_ILLEGAL(HttpStatus.BAD_REQUEST, "该学生不可操作"),
    COURSE_ILLEGAL(HttpStatus.BAD_REQUEST, "该课程不可操作")
    ;

    private Integer code;
    private String message;

    TeacherErrorCodeEnum(HttpStatus httpStatus, String message) {
        this.code = httpStatus.value();
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}