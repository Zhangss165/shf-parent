package com.zss.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zss.entity.Admin;
import com.zss.entity.Permission;
import com.zss.service.AdminService;
import com.zss.service.PermissionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/12 17:47<br/>
 *
 * @author 16574<br />
 */
@Controller
public class IndexController {
    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService;

    //去首页
//    @RequestMapping("/")
//    public String index(){
//        return "frame/index";
//    }

    @RequestMapping("/")
    public String index(ModelMap model){
//        Long adminId = 1L;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Admin admin = adminService.getByUsername(user.getUsername());
        //获取菜单的方法
        List<Permission>  permissionList = permissionService.findMenuPermissionByAdminId(admin.getId());
        model.addAttribute("admin", admin);
        model.addAttribute("permissionList", permissionList);
        return "frame/index";
    }
    //去main
    @RequestMapping("/main")
    public String main(){
        return "frame/main";
    }

    @RequestMapping("/login")
    public String login(){
        return "frame/login";
    }
    @RequestMapping("/auth")
    public String auth(){
        return "frame/auth";
    }
}
