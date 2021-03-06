package com.beitu.saas.app.application.credit.vo;

import com.beitu.saas.common.utils.LocationUtil;
import com.beitu.saas.credit.domain.SaasCreditCarrierRecordVo;

import java.io.Serializable;

/**
 * Created by linchengyu on 17/6/27.
 */
public class CreditCarrierContactRegionListVo implements Serializable {
    
    /**
     * 归属地
     */
    private String location;
    
    /**
     * 呼入呼出
     */
    private String inAndOut;
    
    /**
     * 主叫/被叫时长
     */
    private String inAndOutDuration;
    
    /**
     * 时长占比
     */
    private String percentage;
    
    public CreditCarrierContactRegionListVo(SaasCreditCarrierRecordVo saasCreditCarrierRecordVo, Integer total) {
        location = LocationUtil.formatLocation(saasCreditCarrierRecordVo.getLocation());
        inAndOut = (saasCreditCarrierRecordVo.getCalledTime() == null ? 0 : saasCreditCarrierRecordVo.getCalledTime()) + "/" + (saasCreditCarrierRecordVo.getCallingTime() == null ? 0 : saasCreditCarrierRecordVo.getCallingTime());
        String inDuration = (saasCreditCarrierRecordVo.getCalledDuration() / 60 + ((saasCreditCarrierRecordVo.getCalledDuration() % 60 > 0) ? 1 : 0)) + "分";
        String outDuration = (saasCreditCarrierRecordVo.getCallingDuration() / 60 + ((saasCreditCarrierRecordVo.getCallingDuration() % 60 > 0) ? 1 : 0)) + "分";
        inAndOutDuration = inDuration + "/" + outDuration;
        if (total != null) {
            percentage = Math.round(100F * saasCreditCarrierRecordVo.getTotalDuration() / total) + "%";
        }
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getInAndOut() {
        return inAndOut;
    }
    
    public void setInAndOut(String inAndOut) {
        this.inAndOut = inAndOut;
    }
    
    public String getInAndOutDuration() {
        return inAndOutDuration;
    }
    
    public void setInAndOutDuration(String inAndOutDuration) {
        this.inAndOutDuration = inAndOutDuration;
    }
    
    public String getPercentage() {
        return percentage;
    }
    
    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
