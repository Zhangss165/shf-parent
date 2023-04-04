package com.zss.service;



import com.zss.entity.Role;

import java.util.List;
import java.util.Map;


/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/11 14:44<br/>
 *
 * @author 16574<br />
 */

public interface RoleService extends BaseService<Role> {
    List<Role> findAll();

    Map<String, Object> findRoleByAdminId(Long adminId);

    void saveUserRoleRealtionShip(Long adminId, Long[] roleIds);
}
