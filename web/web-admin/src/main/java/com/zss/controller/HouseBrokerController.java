package com.zss.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zss.entity.Admin;
import com.zss.entity.HouseBroker;
import com.zss.service.AdminService;
import com.zss.service.HouseBrokerService;
import com.zss.service.HouseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/20 15:13<br/>
 *
 * @author 16574<br />
 */
@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController extends BaseController<HouseBroker> {
    @Reference
    private AdminService adminService;
    @Reference
    private HouseBrokerService houseBrokerService;

    //去新增页面
    @RequestMapping("/create")
    public String create(@RequestParam("houseId") Long houseId, ModelMap modelMap) {
        //页面需要传入houseId 和adminList到域中
        modelMap.put("houseId", houseId);
        List<Admin> adminList = adminService.findAll();
        modelMap.put("adminList", adminList);
        return "houseBroker/create";
    }

    //执行新增
    @RequestMapping("/save")
    public String insert(HouseBroker houseBroker) {
        Admin adminServiceById = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(adminServiceById.getName());
        houseBroker.setBrokerHeadUrl(adminServiceById.getHeadUrl());
        houseBrokerService.insert(houseBroker);
        return "common/successPage";
    }

    //去更新的页面
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap modelMap) {
        HouseBroker houseBroker = houseBrokerService.getById(id);
        modelMap.addAttribute("houseBroker", houseBroker);
        List<Admin> adminList = adminService.findAll();
        modelMap.put("adminList", adminList);
        return "houseBroker/edit";
    }

    //执行更新
    @RequestMapping("/update")
    public String update(HouseBroker houseBroker) {
        Admin adminServiceById = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(adminServiceById.getName());
        houseBroker.setBrokerHeadUrl(adminServiceById.getHeadUrl());
        houseBrokerService.update(houseBroker);
        return "common/successPage";
    }

    //删除
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("id") Long id,@PathVariable("houseId") Long houseId) {
        houseBrokerService.delete(id);
        return "redirect:/house/" + houseId;
    }
}