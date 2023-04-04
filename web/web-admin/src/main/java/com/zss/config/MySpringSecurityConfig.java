package com.zss.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/27 10:16<br/>
 *
 * @author 16574<br />
 */
@Configuration //作为一个配置类。需要扫描。
@EnableWebSecurity  //开启SpringSecurity 的注解
@EnableGlobalMethodSecurity(prePostEnabled = true)  //开启controller方法权限控制
public class MySpringSecurityConfig  extends WebSecurityConfigurerAdapter {
    //
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//            .withUser("admin")
//            .password(new BCryptPasswordEncoder().encode("123456"))
//            .roles("");
//    }
//    //设置加密方式,必须指定加密方式，上下加密方式要一致
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //这个目前不能删除, 必须调用父类的方法，否则就不需要认证即可访问，删除的话，需要配置
//        super.configure(http);

        //允许iframe嵌套显示
        //http.headers().frameOptions().disable();
        //允许iframe显示
        http.headers().frameOptions().sameOrigin();

        http.authorizeRequests()
            .antMatchers("/static/**","/login").permitAll() //允许匿名访问的路径
            .anyRequest().authenticated();//其他的都不允许
        http.formLogin().loginPage("/login") //用户未登录时，访问任何需要权限的资源都转跳到该路径，即登录页面，此时登陆成功后会继续跳转到第一次访问的资源页面（相当于被过滤了一下）
            .defaultSuccessUrl("/"); //登录成功后默认跳转到这个页面。
        http.logout().logoutUrl("/logout") //退出登陆的路径，指定spring security拦截的注销url,退出功能是security提供的
            .logoutSuccessUrl("/login"); //用户退出后要被重定向的url
        http.csrf().disable(); ////关闭跨域请求伪造功能
        //添加自定义异常入口
        http.exceptionHandling().accessDeniedHandler( new CustomAccessDeineHandler());
    }
}
