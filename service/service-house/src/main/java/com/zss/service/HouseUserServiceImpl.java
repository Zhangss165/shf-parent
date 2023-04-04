package com.zss.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zss.dao.BaseDao;
import com.zss.dao.HouseUserDao;
import com.zss.entity.HouseUser;
import com.zss.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/20 11:54<br/>
 *
 * @author 16574<br />
 */
@Service(interfaceClass = HouseUserService.class)
@Transactional
public class HouseUserServiceImpl extends BaseServiceImpl<HouseUser> implements HouseUserService {
   @Autowired
   private HouseUserDao houseUserDao;
    @Override
    protected BaseDao<HouseUser> getEntityDao() {
        return houseUserDao;
    }

    @Override
    public List<HouseUser> getHouseUserById(Long houseId) {
        return houseUserDao.getHouseUserById(houseId);
    }
}
