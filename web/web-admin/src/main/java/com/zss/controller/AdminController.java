package com.zss.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.zss.entity.Admin;
import com.zss.service.AdminService;
import com.zss.service.RoleService;
import com.zss.util.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/13 16:54<br/>
 *
 * @author 16574<br />
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController<Admin> {
    @Reference
    private AdminService adminService;
    @Reference
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final String PAGE_INDEX = "admin/index";
    private static final String MESSAGE_SUCCESS = "common/successPage";
    //跳转到新增页面增加
    @RequestMapping("/create")
    public String insert(){
        return "admin/create";
    //执行增加
    }
    @RequestMapping("/save")
    public String save(Admin admin){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        //设置默认头像
        admin.setHeadUrl("http://47.93.148.192:8080/group1/M00/03/F0/rBHu8mHqbpSAU0jVAAAgiJmKg0o148.jpg");


        adminService.insert(admin);
        return MESSAGE_SUCCESS;
    }
    //删除
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        adminService.delete(id);
        return "redirect:/admin";
    }


    //修改 1,根据id获取admin对象
    @RequestMapping("/edit/{id}")
    public String getById(@PathVariable("id") Long id,ModelMap modelMap){
        Admin admin   = adminService.getById(id);
        modelMap.addAttribute("admin",admin);
        return "admin/edit";
    }
    //修改 2，执行保存
    @RequestMapping("/update")
    public  String update(Admin admin){
        adminService.update(admin);
        return "common/successPage";
    }

    //分页查询及条件
    @RequestMapping
    public String findPage(ModelMap modelMap , HttpServletRequest request){
        Map<String, Object> filters = getFilters(request);
        PageInfo<Admin> page = adminService.findPage(filters);
        modelMap.addAttribute("filters",filters);
        modelMap.addAttribute("page",page);
        return PAGE_INDEX;
    }
    //去上传头像的页面
    @RequestMapping("/uploadShow/{id}")
    public  String uploadShow(ModelMap modelMap ,@PathVariable("id") Long id){
        modelMap.addAttribute("id",id);
        return "admin/upload";
    }
    //长传头像
    @RequestMapping("/upload/{id}")
    public  String upload(@PathVariable("id") Long id,@RequestParam(value = "file") MultipartFile file ,HttpServletRequest request){

        try {
            //获取admin对象通过id
            Admin admin = adminService.getById(id);
            //获取字节数组
            byte[] bytes = file.getBytes();
            //设置一个新的名字保证不随机
            String fileName = UUID.randomUUID().toString();
            //通过QiniuUtil上传文件到七牛云
            QiniuUtil.upload2Qiniu(bytes, fileName);
            

            //设置admin的url
            admin.setHeadUrl("http://zhangss.cloud/" + fileName);

            //更新admin
            adminService.update(admin);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "common/successPage" ;
    }

    //去分配角色的页面
    @RequestMapping("/assignShow/{adminId}")
    public String assignShow(ModelMap modelMap,@PathVariable("adminId") Long adminId){
        Map<String,Object>  findRoleByAdminId  = roleService.findRoleByAdminId(adminId);
        //将map集合放到域中
        modelMap.addAttribute("adminId",adminId);
        modelMap.addAllAttributes(findRoleByAdminId);
        return "admin/assignShow";
    }
    //
    @RequestMapping("/assignRole")
    public  String assignRole(Long adminId,Long[] roleIds){
        roleService.saveUserRoleRealtionShip(adminId,roleIds);
        return "common/successPage";
    }

}
