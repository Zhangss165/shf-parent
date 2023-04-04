package com.zss;

import com.zss.dao.DictDao;
import com.zss.entity.Dict;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/18 19:58<br/>
 *
 * @author 16574<br />
 */
@ContextConfiguration(locations = "classpath:spring/spring-dao.xml")
@RunWith(SpringRunner.class)
public class DIctTest {

    @Autowired
    private DictDao dictDao;
    @Test
    public void dictDao(){
        List<Dict> byParentIds = dictDao.findByParentIds(1L);
        for (Dict byParentId : byParentIds) {
            System.out.println("byParentId" + byParentId );
        }
    }
}
