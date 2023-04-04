package com.zss.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.zss.entity.*;


import com.zss.service.*;
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
 * date: 2022/10/18 20:27<br/>
 *
 * @author 16574<br />
 */
@Controller
@RequestMapping("/house")
public class HouseController extends BaseController<House> {

    @Reference
    private HouseService houseService;
    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private HouseUserService houseUserService;
    private static final String HOUSE_INDEX = "house/index";
    private static final String HOUSE_CREATE = "house/create";
    private static final String HOUSE_SUCCESS = "common/successPage";
    private static final String HOUSE_EDIT = "house/edit";
    private static final String HOUSE_DELETE = "redirect:/house";


    //带条件的分页查询
    @RequestMapping
    public String index(ModelMap modelMap, HttpServletRequest request){
        Map<String, Object> filters = getFilters(request);

        modelMap.addAttribute("filters",filters);
        PageInfo<House> pageInfo    = houseService.findPage(filters);
        modelMap.addAttribute("page",pageInfo);

        requestInfo(modelMap);
        return HOUSE_INDEX;
    }
    //跳转到添加页面
    @RequestMapping("/create")
    public String toCreat(ModelMap modelMap){
        requestInfo(modelMap);
        return HOUSE_CREATE;
    }
    //执行添加
    @RequestMapping("/save")
    public String save(House house){
        houseService.insert(house);
        return HOUSE_SUCCESS;
    }
    //去更新页面,根据id查询所有信息并回显
    @RequestMapping("/edit/{id}")
    public String getById(@PathVariable("id") Long id, ModelMap modelMap){
        requestInfo(modelMap);
        House house = houseService.getById(id);
        modelMap.addAttribute("house",house);
        return HOUSE_EDIT;
    }
    //进行更新
    @RequestMapping("/update")
    public String update(House house){
        houseService.update(house);
        return HOUSE_SUCCESS;
    }
    //删除
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        houseService.delete(id);
        return HOUSE_DELETE;
    }

    @RequestMapping("/publish/{id}/{status}")
    public String publish( @PathVariable("id") Long id, @PathVariable("status") Integer status){
        houseService.publish(id,status);
        return HOUSE_DELETE;
    }

    //将信息的放到请求域中的方法
    public void requestInfo(ModelMap modelMap){
        //查询所有小区
        List<Community> communityList = communityService.findAll();
        //查询所有户型
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");
        //查询所有楼层
        List<Dict> floorList = dictService.findListByDictCode("floor");
        //查询所有建筑结构
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");
        //查询朝向
        List<Dict> directionList = dictService.findListByDictCode("direction");
        //查询装修情况
        List<Dict> decorationList = dictService.findListByDictCode("decoration");
        //查询房屋用途
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");
        //存到域中
        modelMap.put("communityList",communityList);
        modelMap.put("houseTypeList",houseTypeList);
        modelMap.put("floorList",floorList);
        modelMap.put("buildStructureList",buildStructureList);
        modelMap.put("directionList",directionList);
        modelMap.put("decorationList",decorationList);
        modelMap.put("houseUseList",houseUseList);

    }
    //详情
    @RequestMapping("/{id}")
    public String show(@PathVariable("id") Long id,ModelMap modelMap){

       //房源信息
        House houseServiceById = houseService.getById(id);
        modelMap.addAttribute("house",houseServiceById);
        //小区信息
        Community communityServiceById = communityService.getById(houseServiceById.getCommunityId());
        modelMap.addAttribute("community",communityServiceById);
        //获取房源图片
        List<HouseImage> houseImagesByIdAndType = houseImageService.getHouseImagesByIdAndType(id, 1);
        //获取房产图片
        List<HouseImage> houseImagesByIdAndType1 = houseImageService.getHouseImagesByIdAndType(id, 2);
        //获取经纪人
        List<HouseBroker> houseBrokerList = houseBrokerService.HouseBrokerByHouseID(id);
        //获取房东信息
        List<HouseUser> houseUserList = houseUserService.getHouseUserById(id);
        //存放到域中
        modelMap.addAttribute("houseImage1List",houseImagesByIdAndType);
        modelMap.addAttribute("houseImage2List",houseImagesByIdAndType1);
        modelMap.addAttribute("houseBrokerList",houseBrokerList);
        modelMap.addAttribute("houseUserList",houseUserList);
        return "house/show";
    }

}
