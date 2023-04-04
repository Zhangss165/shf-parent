package com.zss.service;


import com.alibaba.dubbo.config.annotation.Service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zss.dao.BaseDao;
import com.zss.dao.DictDao;
import com.zss.dao.HouseDao;

import com.zss.entity.House;
import com.zss.service.impl.BaseServiceImpl;
import com.zss.vo.HouseQueryVo;


import com.zss.vo.HouseVo;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/18 20:33<br/>
 *
 * @author 16574<br />
 */
@Service(interfaceClass = HouseService.class)
@Transactional
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {
    @Resource
    private HouseDao houseDao;
    @Resource
    private DictDao dictDao;
    @Override
    protected BaseDao<House> getEntityDao() {
        return houseDao;
    }

    @Override
    public void publish(Long id, Integer status) {
        House house = new House();
        house.setId(id);
        house.setStatus(status);
        houseDao.update(house);
    }
    //调用houseService的findPageList方法
    @Override
    public PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize,HouseQueryVo houseQueryVo) {
        //开启分页插件
        PageHelper.startPage(pageNum,pageSize);
        List<HouseVo> pageList = houseDao.findPageList(houseQueryVo);
        for (HouseVo houseVo : pageList) {
            //户型
            String houseTypeName = dictDao.getNameById(houseVo.getHouseTypeId());
            //楼层
            String floorName = dictDao.getNameById(houseVo.getFloorId());
            //朝向
            String directionName = dictDao.getNameById(houseVo.getDirectionId());
            //设置
            houseVo.setHouseTypeName(houseTypeName);
            houseVo.setFloorName(floorName);
            houseVo.setDirectionName(directionName);
        }
        return new PageInfo<>(pageList, 10);
    }

    //重写getById 因为查看详情原来信息中没有 房屋户型：所在楼层,建筑结构，房屋朝向，装修情况，房屋用途，，所以在他在service中重写一下
    @Override
    public House getById(Long id) {
        House house = houseDao.getById(id);
        //房屋户型
        String houseTypeName = dictDao.getNameById(house.getHouseTypeId());
        //所在楼层
        String floorName = dictDao.getNameById(house.getFloorId());
        //结构
        String buildStructureName = dictDao.getNameById(house.getBuildStructureId());
        //朝向
        String directionName = dictDao.getNameById(house.getDirectionId());
        //装修情况
        String decorationName = dictDao.getNameById(house.getDecorationId());
        //用途
        String houseUserName = dictDao.getNameById(house.getHouseUseId());
        //存到house
        house.setHouseTypeName(houseTypeName);
        house.setFloorName(floorName);
        house.setBuildStructureName(buildStructureName);
        house.setDirectionName(directionName);
        house.setDecorationName(decorationName);
        house.setHouseUseName(houseUserName);
        return house;
    }
}
