package com.zss.service;


import com.zss.entity.HouseBroker;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/20 11:19<br/>
 *
 * @author 16574<br />
 */
public interface HouseBrokerService extends BaseService<HouseBroker>{
    //根据house_id查询经济人的信息
    List<HouseBroker> HouseBrokerByHouseID(Long houseId);
}
