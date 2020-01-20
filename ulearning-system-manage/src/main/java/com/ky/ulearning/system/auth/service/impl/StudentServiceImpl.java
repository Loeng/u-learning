package com.ky.ulearning.system.auth.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.exceptions.exception.EntityExistException;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.StudentDto;
import com.ky.ulearning.spi.system.entity.StudentEntity;
import com.ky.ulearning.system.auth.dao.StudentDao;
import com.ky.ulearning.system.auth.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 学生service - 实现类
 *
 * @author luyuhao
 * @since 20/01/18 22:54
 */
@Service
@CacheConfig(cacheNames = "student")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class StudentServiceImpl extends BaseService implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void save(StudentDto studentDto) {
        //判断学生学号是否存
        StudentEntity stuNumberExists = studentDao.getByStuNumber(studentDto.getStuNumber());
        if (stuNumberExists != null) {
            throw new EntityExistException("学号");
        }
        //判断学生邮箱是否存在
        if (StringUtil.isNotEmpty(studentDto.getStuEmail())
                && studentDao.getByStuEmail(studentDto.getStuEmail()) != null) {
            throw new EntityExistException("邮箱");
        }
        studentDao.insert(studentDto);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public StudentEntity getById(Long id) {
        return studentDao.getById(id);
    }

    @Override
    public PageBean<StudentEntity> pageStudentList(StudentDto studentDto, PageParam pageParam) {
        List<StudentEntity> studentList = studentDao.listPage(studentDto, pageParam);

        PageBean<StudentEntity> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(studentDao.countListPage(studentDto))
                //设置查询结果
                .setContent(studentList);
        return setPageBeanProperties(pageBean, pageParam);
    }
}
