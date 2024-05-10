package com.zulfiqor.z_crm_zulfiqor.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class BaseResponse<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;


    public BaseResponse() {
    }

    public BaseResponse(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public BaseResponse(boolean success, Integer code, T data) {
        this.success = success;
        this.code = code;
        this.data = data;
    }

    public BaseResponse(boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
        this.code = 200;
        this.message = "success";
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(true, 200, "SUCCESS");
    }
    public static <T> BaseResponse<T> success(String message) {
        return new BaseResponse<>(true, 200, message);
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, data);
    }

    public static <T> BaseResponse<T> error(Integer code, String message) {
        return new BaseResponse<>(false, code, message);
    }
}
