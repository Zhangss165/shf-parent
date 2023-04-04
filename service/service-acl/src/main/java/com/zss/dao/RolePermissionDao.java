package com.zss.dao;

import com.zss.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/26 10:16<br/>
 *
 * @author 16574<br />
 */
public interface RolePermissionDao extends BaseDao<RolePermission>{
    List<Long> findPermissionsByRolerId(Long roleId);
    void deleteByRoleId(Long roleId);

    void insertByRoleIdAndPermissionId(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
}
