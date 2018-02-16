package org.spring.Service;

import org.spring.Exception.RepeatKillException;
import org.spring.Exception.SeckillCloseException;
import org.spring.Exception.SeckillException;
import org.spring.dto.Exposer;
import org.spring.dto.SeckillExection;
import org.spring.entity.Seckill;

import java.util.List;

public interface SeckillService {

    List<Seckill> getSeckillList();

    Seckill getById(long seckillId);

    Exposer exportSeckillUrl(long seckillId);

    SeckillExection executeSeckill(long seckillid,long userPhone,String md5) throws SeckillException,RepeatKillException,SeckillCloseException;


}
