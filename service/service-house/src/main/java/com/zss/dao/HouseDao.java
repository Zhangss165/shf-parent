package com.zss.dao;

import com.zss.entity.House;
import com.zss.vo.HouseQueryVo;
import com.zss.vo.HouseVo;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/18 20:35<br/>
 *
 * @author 16574<br />
 */
public interface HouseDao extends BaseDao<House>{

    List<HouseVo> findPageList(HouseQueryVo houseQueryVo);
}
