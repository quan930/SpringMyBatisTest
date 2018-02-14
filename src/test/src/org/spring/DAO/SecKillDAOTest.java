package org.spring.DAO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SecKillDAOTest {

    @Resource
    private SecKillDAO secKillDAO;

    @Test
    public void queryById() {
        Seckill seckill = secKillDAO.queryById(1000);
        System.out.println(seckill.getName());
        System.out.println(seckill);
        /*
            Seckill{
            seckillId=1000,
            name='1000元秒杀iphone6',
            number=100,
            startTime=Fri Jan 01 14:00:00 CST 2016,
            endTime=Sat Jan 02 14:00:00 CST 2016,
            createTime=Wed Feb 14 02:51:02 CST 2018}
         */
    }

    @Test
    public void queryAll() {
        List<Seckill> seckills = secKillDAO.queryAll(0, 100);
        for (Seckill seckill:seckills){
            System.out.println(seckill);
        }

        /*
            java 不保存形参的记录，(int offset,int limit) -> (arg0,arg1)
            所以接口需添加@Param("参数名 ")
            Seckill{seckillId=1000, name='1000元秒杀iphone6', number=100, startTime=Fri Jan 01 14:00:00 CST 2016, endTime=Sat Jan 02 14:00:00 CST 2016, createTime=Wed Feb 14 02:51:02 CST 2018}
            Seckill{seckillId=1001, name='800元秒杀ipad', number=200, startTime=Fri Jan 01 14:00:00 CST 2016, endTime=Sat Jan 02 14:00:00 CST 2016, createTime=Wed Feb 14 02:51:02 CST 2018}
            Seckill{seckillId=1002, name='6600元秒杀mac book pro', number=300, startTime=Fri Jan 01 14:00:00 CST 2016, endTime=Sat Jan 02 14:00:00 CST 2016, createTime=Wed Feb 14 02:51:02 CST 2018}
            Seckill{seckillId=1003, name='7000元秒杀iMac', number=400, startTime=Fri Jan 01 14:00:00 CST 2016, endTime=Sat Jan 02 14:00:00 CST 2016, createTime=Wed Feb 14 02:51:02 CST 2018}
         */
    }

    @Test
    public void reduceNumber() {
        Date date = new Date();
        int i = secKillDAO.reduceNumber(1000L, date);
        System.out.println(i);
    }
}