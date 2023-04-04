package com.zss.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zss.dao.BaseDao;
import com.zss.dao.DictDao;
import com.zss.entity.Dict;
import com.zss.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/17 15:04<br/>
 *
 * @author 16574<br />
 */
@Service(interfaceClass = DictService.class)
@Transactional
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictService{

    @Autowired
    private DictDao dictDao;

    @Override
    public List<Map<String, Object>> findByParentId(Long id) {
        // 返回数据[{ id:2, isParent:true, name:"随意勾选 2"}]
        //根据id获取子节点数据
        //判断该节点是否是父节点

        //获取子节点数据列表
         List<Dict> dictList  =  dictDao.findByParentIds(id);
         //构建ztree数据
        List<Map<String,Object>>  zMap  = new ArrayList<>();
        for ( Dict dictLists : dictList){
            Integer count  = dictDao.countIsParent(id);
            Map<String, Object> map = new HashMap<>();
            map.put("id",dictLists.getId());
            map.put("name",dictLists.getName());
            map.put("isParent",count > 0 ? true :false);
            zMap.add(map);

        }
        return zMap;
    }

    @Override
    public List<Dict> findListByParentId(Long parentId) {
        return dictDao.findByParentIds(parentId);
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        Dict dict  = dictDao.findListByDictCode(dictCode);
        if (dict == null) return  null;
        return this.findListByParentId(dict.getId());
    }

    @Override
    public String getNameById(Long id) {
        return dictDao.getNameById(id);
    }


    @Override
    protected BaseDao<Dict> getEntityDao() {
        return dictDao;
    }


}
