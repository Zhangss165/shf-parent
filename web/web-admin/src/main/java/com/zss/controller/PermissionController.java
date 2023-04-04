package com.zss.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zss.entity.Permission;
import com.zss.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/26 16:34<br/>
 *
 * @author 16574<br />
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    @RequestMapping()
    public String index(ModelMap modelMap){
         List<Permission> list   = permissionService.findAllMenu();
         modelMap.addAttribute("list", list);
         return "permission/index";
    }
    //去新增页面
    @RequestMapping("/create")
    public String create(ModelMap modelMap,Permission permission){
        modelMap.addAttribute("permission", permission);
        return "permission/create";
    }
    //执行新增
    @RequestMapping("/save")
    public String save(Permission permission){
        permissionService.insert(permission);
        return "common/successPage";
    }
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id")Long id) {
        permissionService.delete(id);
        return "redirect:/permission";
    }
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")Long id,ModelMap modelMap){
        Permission permission = permissionService.getById(id);
        modelMap.addAttribute("permission", permission);
        return "permission/edit";
    }
    @RequestMapping("/update")
    public String update(Permission permission){
    permissionService.update(permission);
        return "common/successPage";}
}
