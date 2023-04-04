package com.zss.dao;

import com.zss.entity.Community;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/17 17:44<br/>
 *
 * @author 16574<br />
 */
public interface CommunityDao extends BaseDao<Community> {
    List<Community> findAll();
}
