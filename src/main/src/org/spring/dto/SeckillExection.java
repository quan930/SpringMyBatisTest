package org.spring.dto;


import org.spring.Enums.SecKillStatEnum;
import org.spring.entity.SuccessKilled;

/**
 *
 * 封装秒杀后返回结果
 *
 */

public class SeckillExection {

    private long seckillId;

    private int state;

    private String stateInfo;

    private SuccessKilled successKilled;

    public SeckillExection(long seckillId, SecKillStatEnum secKillStatEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = secKillStatEnum.getState();
        this.stateInfo = secKillStatEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    public SeckillExection(long seckillId, SecKillStatEnum secKillStatEnum) {
        this.seckillId = seckillId;
        this.state = secKillStatEnum.getState();
        this.stateInfo = secKillStatEnum.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
