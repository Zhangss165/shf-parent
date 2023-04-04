package com.zss.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.zss.entity.Community;
import com.zss.entity.Dict;
import com.zss.service.CommunityService;
import com.zss.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/17 17:46<br/>
 *
 * @author 16574<br />
 */
@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController<Community> {
    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService;

    private static final String COMMUNITY_PAGE_INDEX = "community/index";
    private static final String COMMUNITY_PAGE_SUCCESS = "common/successPage";
    private static final String COMMUNITY_PAGE_EDIT = "community/edit";


    @RequestMapping
    public String index(ModelMap modelMap , HttpServletRequest request){
        Map<String, Object> filters = getFilters(request);
        //页面filters.areaId报错，兼容处理
        if(!filters.containsKey("areaId")) {
            filters.put("areaId", "");
        }
        if(!filters.containsKey("plateId")) {
            filters.put("plateId", "");
        }
        modelMap.addAttribute("filters",filters);

        PageInfo<Community> pageInfo = communityService.findPage(filters);

        modelMap.addAttribute("page",pageInfo);
        List<Dict> beijing = dictService.findListByDictCode("beijing");


        modelMap.addAttribute("areaList",beijing);
        return COMMUNITY_PAGE_INDEX;
    }
    //去添加页面
    @RequestMapping("/create")
    public String goAdd(ModelMap modelMap){
        //根据编码查询dict的信息
        List<Dict> beijing = dictService.findListByDictCode("beijing");
        modelMap.addAttribute("areaList",beijing);
        return "community/create";
    }
    //进行添加
    @RequestMapping("/save")
    public String save(Community community){
        communityService.insert(community);
        return COMMUNITY_PAGE_SUCCESS;
    }
    //去修改页面，根据id获取community信息并回显
    @RequestMapping("/edit/{id}")
    public String getById(@PathVariable("id") Long id, ModelMap modelMap){
        //根据编码查询dict的信息
        List<Dict> beijing = dictService.findListByDictCode("beijing");
        modelMap.addAttribute("areaList",beijing);
        Community communityServiceById = communityService.getById(id);
        modelMap.addAttribute("community",communityServiceById);
        return COMMUNITY_PAGE_EDIT;
    }
    //进行修改
    @RequestMapping("/update")
    public String update(Community community){
        communityService.update(community);
        return COMMUNITY_PAGE_SUCCESS;
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        communityService.delete(id);
        return "redirect:/community";
    }

}
