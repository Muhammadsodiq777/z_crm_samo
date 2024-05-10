package com.zulfiqor.z_crm_zulfiqor.model.dto;

public class ReqHeader {
    private String deviceId;
    private String lang;

    public ReqHeader(String deviceId, String lang) {
        this.deviceId = deviceId;
        this.lang = lang;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
