package com.zss.dao;

import com.github.pagehelper.Page;
import com.zss.entity.UserFollow;
import com.zss.vo.UserFollowVo;
import org.apache.ibatis.annotations.Param;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/24 9:31<br/>
 *
 * @author 16574<br />
 */
public interface UserFollowDao extends BaseDao<UserFollow>{
    Integer getByUserIdAndHouseId(@Param("userId") Long userId,@Param("houseId") Long houseId);

    Page<UserFollowVo> findListPage(@Param("userId")Long userId);


}
