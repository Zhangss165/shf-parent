package com.zss.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.zss.dao.AdminDao;
import com.zss.dao.BaseDao;
import com.zss.entity.Admin;
import com.zss.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/13 16:56<br/>
 *
 * @author 16574<br />
 */
@Service(interfaceClass = AdminService.class)
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {
   @Autowired
    private AdminDao adminDao;
    @Override
    public BaseDao<Admin> getEntityDao() {
        return adminDao;
    }

    @Override
    public List<Admin> findAll() {
        return adminDao.findAll();
    }

    @Override
    public Admin getByUsername(String username) {
        return adminDao.getByUsername(username);
    }

    /**
     * @param id
     * @return
     */

}
