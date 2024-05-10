package com.zulfiqor.z_crm_zulfiqor.exception;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<BaseResponse<?>> notFound(NotFoundException exception) {
        return exception(BaseResponse.error(HttpResponseCode.NOT_FOUND.getCode(), exception.getMessage() == null ? HttpResponseCode.NOT_FOUND.getMessage() : exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse<?>> badRequestException(BadRequestException exception) {
        return exception(BaseResponse.error(400, exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseResponse<?>> exception(Exception exception) {
        return exception(BaseResponse.error(500, exception.getMessage()));
    }

    private ResponseEntity<BaseResponse<?>> exception(BaseResponse<?> response) {
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
