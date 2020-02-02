package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.component.constant.DefaultConfigParameters;
import com.ky.ulearning.common.core.constant.CommonErrorCodeEnum;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.FileUtil;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskNoticeDto;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskNoticeEntity;
import com.ky.ulearning.spi.teacher.vo.NoticeAttachmentVo;
import com.ky.ulearning.teacher.common.constants.TeacherErrorCodeEnum;
import com.ky.ulearning.teacher.common.utils.TeachingTaskValidUtil;
import com.ky.ulearning.teacher.service.TeachingTaskNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

/**
 * 教学任务通告controller
 *
 * @author luyuhao
 * @since 20/01/30 23:39
 */
@Slf4j
@RestController
@Api(tags = "通告管理", description = "通告管理接口")
@RequestMapping(value = "/teachingTaskNotice")
public class TeachingTaskNoticeServiceController extends BaseController {

    @Autowired
    private TeachingTaskNoticeService teachingTaskNoticeService;

    @Autowired
    private TeachingTaskValidUtil teachingTaskValidUtil;

    @Autowired
    private DefaultConfigParameters defaultConfigParameters;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Log("分页查询通告")
    @ApiOperation(value = "分页查询通告", notes = "只能查看自己教学任务的通告")
    @ApiOperationSupport(ignoreParameters = {"id", "noticeAttachment"})
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<TeachingTaskNoticeEntity>>> pageList(PageParam pageParam,
                                                                                   TeachingTaskNoticeDto teachingTaskNoticeDto) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(teachingTaskNoticeDto.getTeachingTaskId()), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL);
        //权限校验
        teachingTaskValidUtil.checkTeachingTask(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class), teachingTaskNoticeDto.getTeachingTaskId());
        PageBean<TeachingTaskNoticeEntity> pageBean = teachingTaskNoticeService.pageList(setPageParam(pageParam), teachingTaskNoticeDto);
        return ResponseEntityUtil.ok(JsonResult.buildData(pageBean));
    }

    @Log("添加附件")
    @ApiOperation(value = "添加附件", notes = "添加附件，支持批量上传，多个文件url和名字逗号分隔")
    @PostMapping("/saveAttachments")
    public ResponseEntity<JsonResult<NoticeAttachmentVo>> saveAttachments(MultipartFile[] attachments) throws IOException {
        ValidateHandler.checkParameter(attachments == null || attachments.length < 1, TeacherErrorCodeEnum.NOTICE_ATTACHMENET_CANNOT_BE_NULL);
        String noticeAttachment = "";
        String noticeAttachmentName = "";
        //遍历校验文件
        for (MultipartFile attachment : attachments) {
            ValidatorBuilder.build()
                    //文件类型篡改校验
                    .on(!FileUtil.fileTypeCheck(attachment), CommonErrorCodeEnum.FILE_TYPE_TAMPER)
                    //文件类型校验
                    .on(!FileUtil.fileTypeRuleCheck(attachment, FileUtil.ATTACHMENT_TYPE), CommonErrorCodeEnum.FILE_TYPE_ERROR)
                    //文件大小校验
                    .on(attachment.getSize() > defaultConfigParameters.getAttachmentMaxSize(), CommonErrorCodeEnum.FILE_SIZE_ERROR)
                    .doValidate().checkResult();
        }
        int index = 0;
        //遍历存储文件
        for (MultipartFile attachment : attachments) {
            if (index != 0) {
                noticeAttachment += ",";
                noticeAttachmentName += ",";
            }
            noticeAttachment += fastDfsClientWrapper.uploadFile(attachment);
            noticeAttachmentName += attachment.getOriginalFilename();
            index++;
        }
        return ResponseEntityUtil.ok(JsonResult.buildData(new NoticeAttachmentVo(noticeAttachment, noticeAttachmentName)));
    }

    @Log("添加通告")
    @ApiOperation(value = "添加通告", notes = "只能操作自己的教学任务的通告")
    @ApiOperationSupport(ignoreParameters = {"id", "noticePostTime"})
    @PostMapping("/save")
    public ResponseEntity<JsonResult> save(TeachingTaskNoticeDto teachingTaskNoticeDto) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(teachingTaskNoticeDto.getTeachingTaskId()), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(teachingTaskNoticeDto.getNoticeTitle()), TeacherErrorCodeEnum.NOTICE_TITLE_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //权限校验
        teachingTaskValidUtil.checkTeachingTask(username, teachingTaskNoticeDto.getTeachingTaskId());
        //设置创建/更新者和提交时间
        teachingTaskNoticeDto.setCreateBy(username);
        teachingTaskNoticeDto.setUpdateBy(username);
        teachingTaskNoticeDto.setNoticePostTime(new Date());
        teachingTaskNoticeService.save(teachingTaskNoticeDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("添加成功"));
    }

    @Log("根据id查询通告")
    @ApiOperation(value = "根据id查询通告", notes = "只能查看自己教学任务的通告")
    @GetMapping("/getById")
    public ResponseEntity<JsonResult<TeachingTaskNoticeEntity>> getById(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), TeacherErrorCodeEnum.ID_CANNOT_BE_NULL);
        TeachingTaskNoticeEntity teachingTaskNoticeEntity = teachingTaskNoticeService.getById(id);
        ValidateHandler.checkParameter(teachingTaskNoticeEntity == null, TeacherErrorCodeEnum.NOTICE_NOT_EXISTS);
        //权限校验
        teachingTaskValidUtil.checkTeachingTask(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class), teachingTaskNoticeEntity.getTeachingTaskId());
        return ResponseEntityUtil.ok(JsonResult.buildData(teachingTaskNoticeEntity));
    }

    @Log("修改通告")
    @ApiOperation(value = "修改通告", notes = "只能操作自己的教学任务的通告")
    @ApiOperationSupport(ignoreParameters = {"teachingTaskId", "noticePostTime"})
    @PostMapping("/update")
    public ResponseEntity<JsonResult> update(TeachingTaskNoticeDto teachingTaskNoticeDto){
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(teachingTaskNoticeDto.getId()), TeacherErrorCodeEnum.ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        //获取原通告对象并校验
        TeachingTaskNoticeEntity teachingTaskNoticeEntity = teachingTaskNoticeService.getById(teachingTaskNoticeDto.getId());
        ValidateHandler.checkParameter(teachingTaskNoticeEntity == null, TeacherErrorCodeEnum.NOTICE_NOT_EXISTS);
        //获取登录用户账号
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //权限校验
        teachingTaskValidUtil.checkTeachingTask(username, teachingTaskNoticeEntity.getTeachingTaskId());
        //设置更新者和教学任务id防止修改
        teachingTaskNoticeDto.setUpdateBy(username);
        teachingTaskNoticeDto.setTeachingTaskId(teachingTaskNoticeEntity.getTeachingTaskId());
        teachingTaskNoticeService.update(teachingTaskNoticeDto);

        //检测被删除的附件url
        Set<String> oldAttachment = StringUtil.strToSet(teachingTaskNoticeEntity.getNoticeAttachment(), ",");
        Set<String> newAttachment = StringUtil.strToSet(teachingTaskNoticeDto.getNoticeAttachment(), ",");
        for (String attachment : oldAttachment) {
            //如果新的附件不包含，说明已被删除
            if (!newAttachment.contains(attachment)) {
                fastDfsClientWrapper.deleteFile(attachment);
            }
        }
        return ResponseEntityUtil.ok(JsonResult.buildMsg("修改成功"));
    }

    @Log("删除通告")
    @ApiOperation(value = "删除通告", notes = "只能操作自己教学任务的通告")
    @GetMapping("/delete")
    public ResponseEntity<JsonResult> delete(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), TeacherErrorCodeEnum.ID_CANNOT_BE_NULL);
        //获取原通告对象并校验
        TeachingTaskNoticeEntity teachingTaskNoticeEntity = teachingTaskNoticeService.getById(id);
        ValidateHandler.checkParameter(teachingTaskNoticeEntity == null, TeacherErrorCodeEnum.NOTICE_NOT_EXISTS);
        //权限校验
        teachingTaskValidUtil.checkTeachingTask(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class), teachingTaskNoticeEntity.getTeachingTaskId());
        teachingTaskNoticeService.delete(id, RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        return ResponseEntityUtil.ok(JsonResult.buildMsg("删除成功"));
    }
}
