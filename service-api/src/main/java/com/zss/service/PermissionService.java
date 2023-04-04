package com.zss.service;

import com.zss.entity.Permission;

import java.util.List;
import java.util.Map;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/26 10:13<br/>
 *
 * @author 16574<br />
 */
public interface PermissionService extends  BaseService<Permission>{
    List<Map<String, Object>> findPermissionsByRolerId(Long roleId);

    void permissionsByRoleIdAndPermissionId(Long roleId, Long[] permissionIds);

    List<Permission> findMenuPermissionByAdminId(Long adminId);

    List<Permission> findAllMenu();

    List<String> getCodesByAdminId(Long id);
}
