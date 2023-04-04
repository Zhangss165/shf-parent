package com.zss.service;

import com.zss.entity.Admin;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/13 16:55<br/>
 *
 * @author 16574<br />
 */

public interface AdminService extends BaseService<Admin> {
    List<Admin> findAll();

    Admin getByUsername(String username);


}
