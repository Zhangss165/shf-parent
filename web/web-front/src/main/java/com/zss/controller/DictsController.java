package com.zss.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zss.entity.Dict;
import com.zss.result.Result;
import com.zss.service.DictService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/22 15:47<br/>
 *
 * @author 16574<br />
 */
@RestController
@RequestMapping("/dict")
public class DictsController {

    @Reference
    private DictService dictService;

    @RequestMapping("/findListByDictCode/{dictCode}")
    public Result dictCode(@PathVariable("dictCode") String dictCode) {
        List<Dict> list = dictService.findListByDictCode(dictCode);
        return Result.ok(list);
    }

    @RequestMapping("/findListByParentId/{parentId}")
    public Result findListByParentId(@PathVariable("parentId") Long parentId) {
        List<Dict> listByParentId = dictService.findListByParentId(parentId);
        return Result.ok(listByParentId);
    }




}
