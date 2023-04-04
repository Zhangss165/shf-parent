package com.zss.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zss.dao.BaseDao;
import com.zss.dao.HouseBrokerDao;
import com.zss.entity.HouseBroker;
import com.zss.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/20 11:22<br/>
 *
 * @author 16574<br />
 */
@Service(interfaceClass = HouseBrokerService.class)
@Transactional
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {
    @Autowired
    private HouseBrokerDao houseBrokerDao;

    @Override
    protected BaseDao<HouseBroker> getEntityDao() {
        return houseBrokerDao;
    }

    @Override
    public List<HouseBroker> HouseBrokerByHouseID(Long houseId) {
        return houseBrokerDao.HouseBrokerByHouseID(houseId);
    }
}
