package com.zss.service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.zss.dao.BaseDao;
import com.zss.dao.PermissionDao;
import com.zss.dao.RolePermissionDao;
import com.zss.entity.Permission;
import com.zss.helper.PermissionHelper;
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
 * date: 2022/10/26 10:14<br/>
 *
 * @author 16574<br />
 */
@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    protected BaseDao<Permission> getEntityDao() {
        return permissionDao;
    }

    @Override
    public List<Map<String, Object>> findPermissionsByRolerId(Long roleId) {
        //获取所有的权限
        List<Permission>  permission = permissionDao.findAll();
        //根据roleId 查询已经分配的权限
        List<Long> permissionsByRoleId = rolePermissionDao.findPermissionsByRolerId(roleId);
        //创建一个返回的集合
        List<Map<String,Object>> zNodes = new ArrayList<>();
        for (Permission permission1 : permission) {
            // { id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
            Map<String, Object> map = new HashMap<>();
            map.put("id", permission1.getId());
            map.put("pId", permission1.getParentId());
            map.put("name", permission1.getName());
           if (permissionsByRoleId.contains(permission1.getId())){
              map.put("checked", true);
           }
            zNodes.add(map);
        }
        return zNodes;
    }

    @Override
    public void permissionsByRoleIdAndPermissionId(Long roleId, Long[] permissionIds) {
        //根据roleId先删除permissionId
        rolePermissionDao.deleteByRoleId(roleId);
        for (Long permissionId : permissionIds) {
            if (permissionId != null){
                //通用permissionDao的添加方法
                rolePermissionDao.insertByRoleIdAndPermissionId(roleId, permissionId);
            }

        }
    }

    @Override
    public List<Permission> findMenuPermissionByAdminId(Long adminId) {
        List<Permission> permissionsList = null;
        if (adminId.intValue() == 1){
            //超级管理员。获取所有的权限
            permissionsList = permissionDao.findAll();
        }else {
            permissionsList = permissionDao.findPermissionsByAdminId(adminId);
        }
        List<Permission> result = PermissionHelper.bulid(permissionsList);
        return result;
    }
    @Override
    public List<Permission> findAllMenu() {
        List<Permission> permissionList = permissionDao.findAll();
        if (CollectionUtils.isEmpty((permissionList))) return null;
        List<Permission> tree = PermissionHelper.bulid(permissionList);

        return tree;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public List<String> getCodesByAdminId(Long id) {
        List<String> permissionsList = null;
        if (id == 1 ){
            //超级管理员
            permissionsList = permissionDao.getAllCodes();
        }else {
            permissionsList = permissionDao.findCodesByAdminId(id);
        }
        return permissionsList;
    }


}
