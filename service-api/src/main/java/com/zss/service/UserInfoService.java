package com.zss.service;

import com.zss.entity.UserInfo;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/23 9:57<br/>
 *
 * @author 16574<br />
 */
public interface UserInfoService extends BaseService<UserInfo>{
     UserInfo getByPhone(String phone);

}
