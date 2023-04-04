package com.zss.dao;

import com.zss.entity.Permission;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/26 10:15<br/>
 *
 * @author 16574<br />
 */
public interface PermissionDao extends BaseDao<Permission>{
    List<Permission> findAll();


    List<Permission> findPermissionsByAdminId(Long adminId);

    //查询所有的code权限
    List<String> getAllCodes();
    //根据id 查询权限
    List<String> findCodesByAdminId(Long id);
}
