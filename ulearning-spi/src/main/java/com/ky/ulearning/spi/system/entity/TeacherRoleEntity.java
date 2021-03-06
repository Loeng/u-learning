package com.ky.ulearning.spi.system.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 教师角色实体类
 *
 * @author luyuhao
 * @date 19/12/08 03:19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TeacherRoleEntity extends BaseEntity {
    /**
	* 教师id
	*/

    private Long teaId;

    /**
     * 角色id
     */
    private Long roleId;
}