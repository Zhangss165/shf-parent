package com.zss.dao;

import com.zss.entity.HouseUser;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/20 11:55<br/>
 *
 * @author 16574<br />
 */
public interface HouseUserDao extends BaseDao<HouseUser>{
    List<HouseUser> getHouseUserById(Long houseId);
}
