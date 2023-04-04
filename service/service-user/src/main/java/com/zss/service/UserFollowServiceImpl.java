package com.zss.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zss.dao.BaseDao;
import com.zss.dao.UserFollowDao;
import com.zss.entity.UserFollow;
import com.zss.service.impl.BaseServiceImpl;
import com.zss.vo.UserFollowVo;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/24 9:30<br/>
 *
 * @author 16574<br />
 */
@Service(interfaceClass = UserFollowService.class)
@Transactional
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {
    @Resource
    private UserFollowDao userFollowDao;
    @Reference
    private DictService dictService;

    @Override
    protected BaseDao<UserFollow> getEntityDao() {
        return userFollowDao;
    }

    @Override
    public void follow(Long userId, Long houseId) {
        UserFollow userFollow = new UserFollow();
        userFollow.setUserId(userId);
        userFollow.setHouseId(houseId);
        userFollowDao.insert(userFollow);
    }

    @Override
    public Boolean  getIsFollow(Long userId, Long houseId) {
        Integer count = userFollowDao.getByUserIdAndHouseId(userId, houseId);
        if(count.intValue() == 0) {
            return false;
        }return true;
    }

    /**
     * @param
     * @param pageNum
     * @param pageSize
     * @return
     */


    @Override
    public PageInfo<UserFollowVo> findListPage(int pageNum, int pageSize, Long userId) {
        PageHelper.startPage(pageNum, pageSize);
        Page<UserFollowVo> page = userFollowDao.findListPage(userId);
        List<UserFollowVo> list = page.getResult();
        for(UserFollowVo userFollowVo : list) {
            //户型：
            String houseTypeName = dictService.getNameById(userFollowVo.getHouseTypeId());
            //楼层
            String floorName = dictService.getNameById(userFollowVo.getFloorId());
            //朝向：
            String directionName = dictService.getNameById(userFollowVo.getDirectionId());
            userFollowVo.setHouseTypeName(houseTypeName);
            userFollowVo.setFloorName(floorName);
            userFollowVo.setDirectionName(directionName);
        }
        return new PageInfo<UserFollowVo>(page, 10);
    }

    @Override
    public void cancelFollow(Long id) {
        userFollowDao.delete(id);
    }


}
