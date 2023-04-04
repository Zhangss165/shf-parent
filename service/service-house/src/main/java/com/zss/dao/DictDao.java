package com.zss.dao;

import com.zss.entity.Dict;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/17 15:10<br/>
 *
 * @author 16574<br />
 */
public interface DictDao extends BaseDao<Dict> {
   //根据id查询所有的自节点
    List<Dict> findByParentIds(Long id);
    //查询加点是否为父节点
    Integer countIsParent(Long id);

    //根据编码查询所有信息
    Dict findListByDictCode(String dictCode);
    //根据id获取名字
   String getNameById(Long id);
}
