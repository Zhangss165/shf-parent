package com.zss.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.zss.entity.UserFollow;
import com.zss.entity.UserInfo;
import com.zss.result.Result;
import com.zss.service.UserFollowService;
import com.zss.vo.UserFollowVo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/24 9:29<br/>
 *
 * @author 16574<br />
 */
@RestController
@RequestMapping("/userFollow")
@CrossOrigin
@SuppressWarnings({"unchecked", "rawtypes"})
public class UserFollowController extends BaseController<UserFollow>{
    @Reference
    private UserFollowService userFollowService;

    @RequestMapping("/auth/follow/{houseId}")
    public Result auths(@PathVariable("houseId") Long houseId, HttpServletRequest request){
        UserInfo userInfer = (UserInfo) request.getSession().getAttribute("user");
        Long userId = userInfer.getId();
        userFollowService.follow(userId, houseId);
        return Result.ok();
    }

    //我的关注
    @GetMapping(value = "/auth/list/{pageNum}/{pageSize}")
    public Result findListPage(@PathVariable Integer pageNum,
                               @PathVariable Integer pageSize,
                               HttpServletRequest request) {
        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("USER");
        Long userId = userInfo.getId();
        PageInfo<UserFollowVo> pageInfo = userFollowService.findListPage(pageNum, pageSize, userId);
        return Result.ok(pageInfo);
    }
    //取消关注
    @RequestMapping("/auth/cancelFollow/{id}")
    public Result cancelFollow(@PathVariable("id") Long id,HttpServletRequest request){
        //调用userFollowService的取消关注方法
        userFollowService.cancelFollow(id);
        return Result.ok();
    }
}
