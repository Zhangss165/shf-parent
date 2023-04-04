package com.zss.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zss.dao.BaseDao;
import com.zss.service.BaseService;
import com.zss.util.CastUtil;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;


/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/13 15:38<br/>
 *
 * @author 16574<br />
 */
@Service
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    protected abstract BaseDao<T> getEntityDao();

    public Integer insert(T t) {
        return getEntityDao().insert(t);
    }

    public void delete(Long id) {
        getEntityDao().delete(id);
    }

    public Integer update(T t) {
        return getEntityDao().update(t);
    }

    public T getById(Long id) {return getEntityDao().getById(id);}

    public PageInfo<T> findPage(Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);

        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<T>(getEntityDao().findPage(filters), 10);
    }
}
