package com.zss.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zss.dao.BaseDao;
import com.zss.dao.UserInfoDao;
import com.zss.entity.UserInfo;
import com.zss.service.impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/23 9:58<br/>
 *
 * @author 16574<br />
 */
@Service(interfaceClass = UserInfoService.class)
@Transactional
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService{
    @Resource
    private UserInfoDao userInfoDao;

    @Override
    protected BaseDao<UserInfo> getEntityDao() {
        return userInfoDao;
    }

    @Override
    public UserInfo getByPhone(String phone) {
        return userInfoDao.getByPhone(phone);
    }
}
fgx