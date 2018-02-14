package org.spring.DAO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKillDAOTest {

    @Resource
    private SuccessKillDAO successKillDAO;

    @Test
    public void insertSuccessKilled() {
        int i = successKillDAO.insertSuccessKilled(1000L, 15012221222L);
        System.out.println(i);
    }

    @Test
    public void queryByIdWithSeckill() {

        SuccessKilled successKilled = successKillDAO.queryByIdWithSeckill(1000L, 35012221222L);

        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());

    }
}