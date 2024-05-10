package com.zulfiqor.z_crm_zulfiqor.exception;

public enum HttpResponseCode {
    SUCCESS(200, "success"),
    TOKEN_EXPIRE(413, "Sizning tokeningiz eskirgan"),
    NOT_FOUND(404, "Malumot topilmadi"),
    NOT_PERMITTED(403, "Ruxsat berilmagan"),
    BAD_REQUEST(400, "Noto'g'ri so'rov yuborildi"),
    INTERNAL_SERVER(500, "Ichki xatolik mavjud"),
    NO_RESOURCE(420, "Malumot yetrali emas"),
    BLOCKED(423, "Siz ushbu servicedan foydalana olmaysiz");

    private int code;
    private String message;

    HttpResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
