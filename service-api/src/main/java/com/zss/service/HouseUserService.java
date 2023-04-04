package com.zss.service;

import com.zss.entity.HouseUser;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/20 11:54<br/>
 *
 * @author 16574<br />
 */
public interface HouseUserService extends BaseService<HouseUser> {

    //根据id 查询房东的信息
    List<HouseUser> getHouseUserById(Long houseId);
}
