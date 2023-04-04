package com.zss.config;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.StringUtil;
import com.zss.entity.Admin;
import com.zss.service.AdminService;
import com.zss.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 202
2/10/27 15:13<br/>
 *
 * @author 16574<br />
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService;

    //登录时。security会自动调用改方法，并将用户传入到改方法中
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username:"+username);
        //调用adminService的方法通过username获取admin对象
        Admin admin = adminService.getByUsername(username);

        if (admin == null) {
            throw new UsernameNotFoundException("用户不正确");
        }
        //给用户授权
        List<String> codesList = permissionService.getCodesByAdminId(admin.getId());
        Collection<GrantedAuthority> grantedAuthorityArrayList = new  ArrayList<>();
        for (String codes : codesList) {
            if (!StringUtil.isEmpty(codes)){
                //simpleGrantedAuthority是GrantedAuthority的实现类，GrantedAuthority是接口
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(codes);
                grantedAuthorityArrayList.add(simpleGrantedAuthority);
            }
        }
        return new User(username,admin.getPassword(),grantedAuthorityArrayList);

    }
}

