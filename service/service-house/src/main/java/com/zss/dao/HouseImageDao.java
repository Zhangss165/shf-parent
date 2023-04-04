package com.zss.dao;

import com.zss.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/20 11:42<br/>
 *
 * @author 16574<br />
 */
public interface HouseImageDao extends BaseDao<HouseImage> {
    List<HouseImage> getHouseImagesByIdAndType(@Param("houseId") Long houseId , @Param("type") Integer type);
}
