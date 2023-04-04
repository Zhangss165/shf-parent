package com.zss.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zss.entity.Dict;
import com.zss.result.Result;
import com.zss.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/17 15:02<br/>
 *
 * @author 16574<br />
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController<Dict>{
    @Reference
    private DictService dictService;
    //去首页
    @RequestMapping
    public String index() {
        return "dict/index";
    }
    //根据id查子节点
    @RequestMapping("/findZnodes")
    @ResponseBody
    public Result findByParentId(@RequestParam(value = "id",defaultValue =" 0") Long id){
        List<Map<String,Object>>  zNodes= dictService.findByParentId(id);
        return Result.ok(zNodes);
    }
    /**
     * 根据上级id获取子节点数据列表
     * @param areaId
     * @return
     */
    @RequestMapping("/findListByParentId/{areaId}")
    @ResponseBody
    public Result<List<Dict>> findListByParentId(@PathVariable("areaId") Long areaId ) {
        List<Dict> listByParentId = dictService.findListByParentId(areaId);
        return Result.ok(listByParentId);
    }
    /**
     * 根据编码获取子节点数据列表
     * @param dictCode
     * @return
     */
    @RequestMapping("/findListByDictCode/{dictCode}")
    @ResponseBody
    public Result<List<Dict>> findListByDictCode(@PathVariable("dictCode") String dictCode) {
        List<Dict> listByDictCode = dictService.findListByDictCode(dictCode);
        return Result.ok(listByDictCode);
    }

}
