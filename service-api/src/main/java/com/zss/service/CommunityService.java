package com.zss.service;

import com.zss.entity.Community;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/17 17:41<br/>
 *
 * @author 16574<br />
 */
public interface CommunityService extends BaseService<Community>{
    List<Community> findAll();
}
