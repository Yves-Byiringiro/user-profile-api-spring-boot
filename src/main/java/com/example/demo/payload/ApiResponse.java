package com.example.demo.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private T data;
    private List<T> dataArray;
    private String message = "";
    private HttpStatus status;
    private String code = "";
    private final String timestamp = LocalDateTime.now().toString();


    public ApiResponse() {
    }

    public ApiResponse(String message, String code, HttpStatus status) {
        this.message = message;
        this.code = code;
        this.status = status;
    }

    public ApiResponse(T data, String code, HttpStatus status) {
        this.data = data;
        this.code = code;
        this.status = status;
    }


    public ApiResponse(T data, String message, String code, HttpStatus status) {
        this.message = message;
        this.code = code;
        this.data = data;
        this.status = status;
    }


    public ApiResponse(List<T> dataArray, String message, String code, HttpStatus status) {
        this.dataArray = dataArray;
        this.message = message;
        this.code = code;
        this.status = status;
    }



    public ResponseEntity<ApiResponse<T>> toResponseEntity() {
        return ResponseEntity.status(this.status).body(this);
    }
}