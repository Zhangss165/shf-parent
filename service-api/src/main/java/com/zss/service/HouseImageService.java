package com.zss.service;

import com.zss.entity.HouseImage;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/20 11:39<br/>
 *
 * @author 16574<br />
 */
public interface HouseImageService extends BaseService<HouseImage> {
    //根据id和类型查图片信息
    List<HouseImage> getHouseImagesByIdAndType (Long houseId,Integer type);
}
