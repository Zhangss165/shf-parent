package com.zss.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zss.dao.BaseDao;
import com.zss.dao.HouseImageDao;
import com.zss.entity.HouseImage;
import com.zss.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/20 11:40<br/>
 *
 * @author 16574<br />
 */
@Service(interfaceClass = HouseImageService.class)
@Transactional
public class HouseImageServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImageService {

    @Autowired
    private HouseImageDao houseImageDao;
    @Override
    protected BaseDao<HouseImage> getEntityDao() {
        return houseImageDao;
    }

    @Override
    public List<HouseImage> getHouseImagesByIdAndType(Long houseId, Integer type) {
        return houseImageDao.getHouseImagesByIdAndType(houseId, type);
    }
}
