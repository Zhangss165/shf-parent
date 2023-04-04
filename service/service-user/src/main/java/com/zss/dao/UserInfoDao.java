package com.zss.dao;

import com.zss.entity.UserInfo;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/23 9:59<br/>
 *
 * @author 16574<br />
 */
public interface UserInfoDao extends BaseDao<UserInfo> {
    UserInfo getByPhone(String phone);
}
