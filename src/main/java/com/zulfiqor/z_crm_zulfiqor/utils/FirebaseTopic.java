package com.zulfiqor.z_crm_zulfiqor.utils;

public enum FirebaseTopic {
    USER_UZ("USER_UZ"),
    USER_RU("USER_RU");

    private String value;

    FirebaseTopic(String value) {
        this.value = value;
    }

    public static String getInstance(String lang) {
        return lang.equals("ru") ? FirebaseTopic.USER_RU.getValue() : FirebaseTopic.USER_UZ.getValue();
    }

    public String getValue() {
        return value;
    }
}
