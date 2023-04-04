package com.zss.service;

import com.github.pagehelper.PageInfo;
import com.zss.entity.UserFollow;
import com.zss.vo.UserFollowVo;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/24 9:29<br/>
 *
 * @author 16574<br />
 */
public interface UserFollowService extends BaseService<UserFollow>{
    //关注
    void follow(Long userId, Long houseId);

    Boolean getIsFollow(Long id, Long houseId);

    PageInfo<UserFollowVo> findListPage(int pageNum, int pageSize, Long userId);
    //取消关注
    void cancelFollow(Long id);
}
