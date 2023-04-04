package com.zss.service;

import com.github.pagehelper.PageInfo;
import com.zss.entity.House;
import com.zss.vo.HouseQueryVo;
import com.zss.vo.HouseVo;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/18 20:31<br/>
 *
 * @author 16574<br />
 */
public interface HouseService extends BaseService<House>{
    void publish(Long id, Integer status);

    //调用houseService的findPageList方法
    PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize, HouseQueryVo  houseQueryVo);
}
