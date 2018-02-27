package org.spring.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.Exception.RepeatKillException;
import org.spring.Exception.SeckillCloseException;
import org.spring.dto.Exposer;
import org.spring.dto.SeckillExection;
import org.spring.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
        })
public class SeckillServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {

        List<Seckill> list = seckillService.getSeckillList();

        logger.info("list={}",list);

    }

    @Test
    public void getById() {

        Seckill seckill = seckillService.getById(1000);

        logger.info("list={}",seckill);

    }

    @Test
    public void exportSeckillUrl() {

        long id = 1001;

        Exposer exposer = seckillService.exportSeckillUrl(id);

        if (exposer.isExposed()){

            logger.info("exposer={}",exposer);

            try {

                SeckillExection seckillExection = seckillService.executeSeckill(id,12312431274L,exposer.getMd5());

                logger.info("seckillExection={}",seckillExection);

            }catch (RepeatKillException e){
                logger.info(e.getMessage());
            }catch (SeckillCloseException e){
                logger.info(e.getMessage());
            }

        }else {

            logger.warn("exposer={}",exposer);

        }

    }

    @Test
    public void executeSeckill() {

        String md5 = "e565da41132a3ae3e52d6ac8655c18d6";

        try {
            SeckillExection seckillExection = seckillService.executeSeckill(1000,12312431274L,md5);
            logger.info("seckillExection={}",seckillExection);
        }catch (RepeatKillException e){
            logger.info(e.getMessage());
        }catch (SeckillCloseException e){
            logger.info(e.getMessage());
        }
    }

    @Test
    public void test(){
        Exposer exposer = seckillService.exportSeckillUrl(1000L);
        if (exposer.isExposed()){
            String md5 = exposer.getMd5();
            SeckillExection exection = seckillService.executeSeckillProcedure(1000L, 138136819111L, md5);
            logger.info(exection.getStateInfo());
        }

    }
}