package com.zss.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.zss.dao.AdminRoleDao;
import com.zss.dao.BaseDao;
import com.zss.dao.RoleDao;
import com.zss.entity.Role;
import com.zss.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/11 14:47<br/>
 *
 * @author 16574<br />
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
   @Autowired
    private AdminRoleDao adminRoleDao;
   @Autowired
   private RoleDao roleDao;

    @Override
    public BaseDao<Role> getEntityDao() {
        return roleDao;
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Map<String, Object> findRoleByAdminId(Long adminId) {
        //查询所有的信息
        List<Role> roleDaoAll = roleDao.findAll();
        //根据adminId查询所有的roleid
        List<Long>  roleList  = adminRoleDao.findRoleByAdminId(adminId);
        //创建两个map，分配的和未分配的
        List<Role> noAssginRoleList = new ArrayList<>();
        List<Role> assginRoleList = new ArrayList<>();
        for (Role role : roleDaoAll) {
            if (roleList.contains(role.getId())){
                assginRoleList.add(role);
            }else {
                noAssginRoleList.add(role);
            }
        }
        //创建一个map
        Map<String, Object> map = new HashMap<>();
        map.put("noAssginRoleList", noAssginRoleList);
        map.put("assginRoleList", assginRoleList);

        return map;
    }

    @Override
    public void saveUserRoleRealtionShip(Long adminId, Long[] roleIds) {
        adminRoleDao.deleteByAdminId(adminId);
        for (Long roleId : roleIds) {
            if(roleId != null ) {
                adminRoleDao.insertAdminIdAndRoleId(roleId, adminId);
            }
        }

    }
}
