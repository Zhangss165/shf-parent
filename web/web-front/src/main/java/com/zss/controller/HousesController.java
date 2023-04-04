package com.zss.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.zss.entity.*;
import com.zss.result.Result;
import com.zss.service.*;
import com.zss.vo.HouseQueryVo;
import com.zss.vo.HouseVo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/22 16:58<br/>
 *
 * @author 16574<br />
 */
@RestController
@RequestMapping("/house")
@CrossOrigin
@SuppressWarnings({"unchecked", "rawtypes"})
public class HousesController {
    @Reference
    private HouseService houseService;
    @Reference
    private CommunityService communityService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private UserFollowService userFollowService;
    //分页查询及带条件的查询，前端页面
    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result findPageList(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize,
                               @RequestBody HouseQueryVo houseQueryVo){
        //调用houseService的findPageList方法获取父节点的所有字节点
        PageInfo<HouseVo> houseQueryVoList = houseService.findPageList(pageNum, pageSize,houseQueryVo);
        return Result.ok(houseQueryVoList);
    }
    //查询房源详情
    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id, HttpServletRequest request){
        //查看小区信息
        House house = houseService.getById(id);
        //查看房源信息
        Community community = communityService.getById(house.getCommunityId());
        //查看经济人的信息
        List<HouseBroker> houseBrokerList = houseBrokerService.HouseBrokerByHouseID(id);
        //查看房源图片信息
        List<HouseImage> houseImage1List = houseImageService.getHouseImagesByIdAndType(id, 1);


        Map<String,Object> map = new HashMap<String,Object>();
        map.put("house",house);
        map.put("community",community);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseImage1List",houseImage1List);
        //设置默认的关注方式
//        map.put("isFollow",false);
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER");
        Boolean isFollow = false;
        if (null != userInfo) {
            isFollow = userFollowService.getIsFollow(userInfo.getId(),id);
        }
        map.put("isFollow",isFollow);
        return Result.ok(map);
    }
}
