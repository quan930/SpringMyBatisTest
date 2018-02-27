package org.spring.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.Exception.RepeatKillException;
import org.spring.Exception.SeckillCloseException;
import org.spring.Service.SeckillService;
import org.spring.dto.Exposer;
import org.spring.dto.SeckillExection;
import org.spring.dto.SeckillResult;
import org.spring.entity.Seckill;
import org.spring.enums.SecKillStatEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    /**
     * 获取列表
     * @param model
     * @return
     */
   @RequestMapping(value = "list",method = RequestMethod.GET)
    public String getList(Model model){

        List<Seckill> list = seckillService.getSeckillList();

        model.addAttribute("list",list);

        return "list";
    }

    /**
     * 秒杀信息详情
     * @param seckillId
     * @param model
     * @return
     */
    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){

        if (seckillId == null){
            return "redirect:/seckill/list";
        }

        Seckill seckill = seckillService.getById(seckillId);

        if (seckill == null){
            return "forward:/seckill/list";
        }

        model.addAttribute("seckill",seckill);

        return "detail";
    }

    /**
     * 获取秒杀地址
     * @param seckillId
     * @return
     */
    @RequestMapping(value = "/{seckillid}/exposer",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public SeckillResult<Exposer> exposer(Long seckillId){

        SeckillResult<Exposer> result;

        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result = new SeckillResult<Exposer>(false,e.getMessage());
        }

        return result;
    }

    /**
     * 执行秒杀逻辑
     * @thorw:
     * SeckillCloseException:秒杀关闭
     * RepeatKillException:重复秒杀
     * Exception:系统异常
     * @param seckillId
     * @param md5
     * @param userPhone
     * @return
     */
    @RequestMapping(value = "/{seckillId}/{md5}/execution",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public SeckillResult<SeckillExection> execute(@PathVariable(value = "seckillId") Long seckillId,
                                                  @PathVariable(value = "md5") String md5,
                                                  @CookieValue(value = "killPhone",required = false) Long userPhone){

        if (userPhone == null){
            return new SeckillResult<SeckillExection>(false,"未注册");
        }

        try {
            SeckillExection exection = seckillService.executeSeckillProcedure(seckillId, userPhone, md5);
            return new SeckillResult<SeckillExection>(true,exection);
        } catch (SeckillCloseException e){
            SeckillExection exection = new SeckillExection(seckillId, SecKillStatEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExection>(false,exection);
        } catch (RepeatKillException e){
            SeckillExection exection = new SeckillExection(seckillId, SecKillStatEnum.END);
            return new SeckillResult<SeckillExection>(false,exection);
        } catch (Exception e){
            SeckillExection exection = new SeckillExection(seckillId, SecKillStatEnum.INNER_ERROR);
            return new SeckillResult<SeckillExection>(false,exection);
        }
    }

    /**
     * 获取服务器时间
     * @return
     */
    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Date> time(){
        Date date = new Date();
        return new SeckillResult<Date>(true,date);
    }

}
