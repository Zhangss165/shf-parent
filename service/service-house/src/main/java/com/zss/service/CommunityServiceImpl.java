package com.zss.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zss.dao.BaseDao;
import com.zss.dao.CommunityDao;
import com.zss.dao.DictDao;
import com.zss.entity.Community;
import com.zss.service.impl.BaseServiceImpl;
import com.zss.util.CastUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/17 17:43<br/>
 *
 * @author 16574<br />
 */
@Service(interfaceClass = CommunityService.class)
@Transactional
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {
    @Autowired
    private CommunityDao communityDao;
    @Autowired
    private DictDao dictDao;
    @Override
    protected BaseDao<Community> getEntityDao() {
        return communityDao;
    }

    @Override
    public PageInfo<Community> findPage(Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);

        PageHelper.startPage(pageNum, pageSize);
        Page<Community> page = communityDao.findPage(filters);
        List<Community> result = page.getResult();
        for (Community community : result){
            String areaName = dictDao.getNameById((community.getAreaId()));
            String plateName = dictDao.getNameById((community.getPlateId()));
            community.setAreaName(areaName);
            community.setPlateName(plateName);
        }
        return new PageInfo<>(page,10);
    }

    @Override
    public List<Community> findAll() {
        return communityDao.findAll();
    }
    //重写getById


    @Override
    public Community getById(Long id) {
        Community community = communityDao.getById(id);
        String areaName = dictDao.getNameById((community.getAreaId()));
        String plateName = dictDao.getNameById((community.getPlateId()));
        community.setAreaName(areaName);
        community.setPlateName(plateName);
        return community;
    }
}
