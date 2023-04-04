package com.zss.dao;




import com.zss.entity.HouseBroker;


import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/20 11:24<br/>
 *
 * @author 16574<br />
 */
public interface HouseBrokerDao extends BaseDao<HouseBroker> {
    //根据house_id查询经济人的信息
    List<HouseBroker> HouseBrokerByHouseID(Long houseId);

}
