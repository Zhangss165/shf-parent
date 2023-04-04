package com.zss.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.zss.entity.Role;
import com.zss.service.PermissionService;
import com.zss.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/11 14:49<br/>
 *
 * @author 16574<br />
 */
@Controller()
@RequestMapping("/role")
public class RoleController extends BaseController<Role> {
    @Reference(loadbalance = "random")
    private RoleService roleService;
    @Reference
    private PermissionService permissionService;

    private static final String PAGE_INDEX = "role/index";
    private static final String PAGE_CREATE = "role/create";
    private static final String LIST_ACTION = "redirect:/role";
    private static final String PAGE_SUCCESS = "common/successPage";
    /*//获取列表
    @RequestMapping
    public String index(ModelMap modelMap){
        List<Role> roleServiceAll = roleService.findAll();
        modelMap.addAttribute("list",roleServiceAll);
        return PAGE_INDEX;
        }
    */
//    @PreAuthorize("hasAnyAuthority('role.show')")
    @RequestMapping()
    public String index(ModelMap modelMap, HttpServletRequest request){

        Map<String, Object> filters = getFilters(request);
        PageInfo<Role> pageInfo    = roleService.findPage(filters);
        modelMap.addAttribute("page",pageInfo);
        modelMap.addAttribute("filters",filters);
        return "role/index";
    }
    //
    //增加
//    @RequestMapping("/save")
//    public String insert(Role role){
//        roleService.insert(role);
//        return LIST_ACTION;
//    }
    @PreAuthorize("hasAnyAuthority('role.create')")
    //跳转新增页面
    @RequestMapping("/create")
    public String create(){
        return PAGE_CREATE;
    }
    //增加
    @PreAuthorize("hasAnyAuthority('role.create')")
    @RequestMapping("/save")
    public String save(Role role ){
        roleService.insert(role);
        return  PAGE_SUCCESS;
    }

    //删除
    @PreAuthorize("hasAnyAuthority('role.delete')")
    @RequestMapping("/delete/{roleId}")
    public String delete(@PathVariable("roleId") Long roleId){
        roleService.delete(roleId);
        return LIST_ACTION;
    }
    @PreAuthorize("hasAnyAuthority('role.edit')")
    //修改 1，根据id获取对象，
    @RequestMapping("/edit/{roleId}")
    public String getById(@PathVariable("roleId") Long roleId, Map map) {
        Role role = (Role) roleService.getById(roleId);
        map.put("role",role);
        return "role/edit";
    }
    @PreAuthorize("hasAnyAuthority('role.edit')")
    //修改 2，执行更新
    @RequestMapping("/update")
    public String update(Role role){
        roleService.update(role);
        return PAGE_SUCCESS;
    }
    @PreAuthorize("hasAnyAuthority('role.assgin')")
    //去权限管理
    @RequestMapping("/assignShow/{roleId}")
    public String toAssignShow(@PathVariable("roleId") Long roleId , ModelMap modelMap){
        modelMap.put("roleId",roleId);
        List<Map<String,Object>> zNodes = permissionService.findPermissionsByRolerId(roleId);
        modelMap.addAttribute("zNodes",zNodes);
        return "role/assignShow";
    }
    @PreAuthorize("hasAnyAuthority('role.assgin')")
    //执行权限管理
    @RequestMapping("/assignPermission")
    public String assignPermission(@RequestParam("roleId") Long roleId,@RequestParam("permissionIds") Long[] permissionIds){
        //调用permissionService的方法进行权限管理
        permissionService.permissionsByRoleIdAndPermissionId(roleId,permissionIds);
        return "common/successPage";
    }





}
