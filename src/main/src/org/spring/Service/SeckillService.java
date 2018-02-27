package org.spring.Service;

import org.spring.Exception.RepeatKillException;
import org.spring.Exception.SeckillCloseException;
import org.spring.Exception.SeckillException;
import org.spring.dto.Exposer;
import org.spring.dto.SeckillExection;
import org.spring.entity.Seckill;

import java.util.List;
import java.util.Map;

public interface SeckillService {
    /**
     * 查询所有列表
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 通过id获取秒杀商品
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 获取秒杀链接
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀
     * @param seckillid
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExection executeSeckill(long seckillid,long userPhone,String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException;

    /**
     * 执行秒杀by procedure
     * @param seckillid
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExection executeSeckillProcedure(long seckillid,long userPhone,String md5);



}
