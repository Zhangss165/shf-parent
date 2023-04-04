package com.zss.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zss.entity.HouseUser;
import com.zss.service.HouseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/21 10:02<br/>
 *
 * @author 16574<br />
 */
@Controller
@RequestMapping("houseUser")
public class HouseUserController {
    @Reference
    private HouseUserService houseUserService;

    //去新增页面
    @RequestMapping("/create")
    public String create(@RequestParam("houseId") String houseId, ModelMap modelMap) {
        modelMap.addAttribute("houseId", houseId);
        return "houseUser/create";
    }

    @RequestMapping("/save")
    public String save(HouseUser houseUser) {
        houseUserService.insert(houseUser);
        return "common/successPage";
    }

    //去修改
    @RequestMapping("/edit/{id}")
    public String update(@PathVariable("id") Long id, ModelMap modelMap) {
        HouseUser houseUser = houseUserService.getById(id);
        modelMap.addAttribute("houseUser", houseUser);
        return "houseUser/edit";
    }

    //执行修改
    @RequestMapping("/update")
    public String update(HouseUser houseUser) {
        houseUserService.update(houseUser);
        return "common/successPage";
    }
    //删除
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId, @PathVariable("id") Long id) {
        houseUserService.delete(id);
        return "redirect:/house/"+ houseId;
    }
}