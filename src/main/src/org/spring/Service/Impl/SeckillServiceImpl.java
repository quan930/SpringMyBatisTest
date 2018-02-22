package org.spring.Service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.DAO.SecKillDAO;
import org.spring.DAO.SuccessKillDAO;
import org.spring.enums.SecKillStatEnum;
import org.spring.Exception.RepeatKillException;
import org.spring.Exception.SeckillCloseException;
import org.spring.Exception.SeckillException;
import org.spring.Service.SeckillService;
import org.spring.dto.Exposer;
import org.spring.dto.SeckillExection;
import org.spring.entity.Seckill;
import org.spring.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {

    //md5盐值字符串
    private final String slat = "jdljFIJDFjijfla&fdsl9(jflaksdY3y&)*HIUHkf";

    //日志
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecKillDAO secKillDAO;

    @Autowired
    private SuccessKillDAO successKillDAO;

    public List<Seckill> getSeckillList() {
        return secKillDAO.queryAll(0,4);
    }

    public Seckill getById(long seckillId) {
        return secKillDAO.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = secKillDAO.queryById(seckillId);

        if (seckill == null){
            return new Exposer(false,seckillId);
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();

        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }

        String md5 = getMD5(seckillId);

        return new Exposer(true,md5,seckillId);
    }

    private String getMD5(long seckillId){
        String base = seckillId + "/" +slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());

        return md5;
    }

    @Transactional
    public SeckillExection executeSeckill(long seckillid, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException {

        if (md5 == null || !md5.equals(getMD5(seckillid))){
            throw  new SeckillException("md5 error");
        }

        //逻辑：减库存 加购买记录
        Date nowTime = new Date();

        try {

            //减库存
            int updateCount = secKillDAO.reduceNumber(seckillid,nowTime);

            if (updateCount <= 0){

                //没有更新到记录，秒杀结束
                throw new SeckillCloseException("seckill is close");

            }else {

                //记录购买行为
                int insertConut = successKillDAO.insertSuccessKilled(seckillid,userPhone);

                if (insertConut <= 0){

                    //重复秒杀异常
                    throw new RepeatKillException("repeat exception");

                }else {

                    //成功，查询到successKilled实体
                    SuccessKilled successKilled = successKillDAO.queryByIdWithSeckill(seckillid,userPhone);

                    //返回成功的实体
                    return new SeckillExection(seckillid, SecKillStatEnum.SUCCESS, successKilled);
                }
            }

        } catch (RepeatKillException e){
            throw e;
        } catch (SeckillCloseException e){
            throw e;
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            //所有编译期异常，转化为运行期异常
            throw new SeckillException("SecKill inner error:"+e.getMessage());
        }

    }
}
