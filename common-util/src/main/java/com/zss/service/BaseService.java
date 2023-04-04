package com.zss.service;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Map;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/13 15:35<br/>
 *
 * @author 16574<br />
 */
public interface BaseService<T> {
    Integer insert(T t);

    void delete(Long id);

    Integer update(T t);

    T getById(Long id);

    PageInfo<T> findPage(Map<String, Object> filters);
}
