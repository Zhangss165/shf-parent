package com.zss.service;

import com.zss.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/17 15:03<br/>
 *
 * @author 16574<br />
 */

public interface DictService extends BaseService<Dict> {
    //查询字典中的数据。通过Ztree 进行渲染
    List<Map<String, Object>> findByParentId(Long id);
    // 根据上级id获取子节点数据列表
    List<Dict> findListByParentId(Long parentId);
    //根据编码获取子节点数据列表
    List<Dict> findListByDictCode(String dictCode);

    String getNameById(Long id);
}
