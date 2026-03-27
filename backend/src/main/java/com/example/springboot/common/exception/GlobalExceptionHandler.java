package com.example.springboot.common.exception;

import com.example.springboot.common.response.ApiResponse;
import com.example.springboot.common.response.ResultCode;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
        return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST.getCode(), ex.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ApiResponse<Void>> handleValidationException(Exception ex) {
        return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST.getCode(), "请求参数不合法"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(ResultCode.FAIL.getCode(), "服务器内部错误"));
    }
}

