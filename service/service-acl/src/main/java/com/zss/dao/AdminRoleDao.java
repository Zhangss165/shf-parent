package com.zss.dao;

import com.zss.entity.AdminRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/25 15:30<br/>
 *
 * @author 16574<br />
 */
public interface AdminRoleDao  extends BaseDao<AdminRole> {
    List<Long> findRoleByAdminId(Long adminId);

    void deleteByAdminId(Long adminId);

    void insertAdminIdAndRoleId(@Param("roleId") Long roleId,@Param("adminId")Long adminId);
}
