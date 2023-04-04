package com.zss.dao;

import com.zss.entity.Role;

import java.util.List;


/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/11 14:38<br/>
 *
 * @author 16574<br />
 */

public interface RoleDao extends BaseDao<Role>{
    List<Role> findAll();

}
