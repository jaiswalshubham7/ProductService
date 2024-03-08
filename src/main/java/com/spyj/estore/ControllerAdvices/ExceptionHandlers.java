package com.spyj.estore.ControllerAdvices;

import com.spyj.estore.DTOs.ArithmeticExceptionDto;
import com.spyj.estore.Exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(){
        return new ResponseEntity<>(
                "Product Not Found", HttpStatusCode.valueOf(404));
    }

//    @ExceptionHandler(ArithmeticException.class)
//    public ResponseEntity<ArithmeticExceptionDto> handleArithmeticException(){
//        ArithmeticExceptionDto arithmeticExceptionDto = ArithmeticExceptionDto.builder().message("Illegal Argument passed.").build();
//        return new ResponseEntity<>(
//                arithmeticExceptionDto, HttpStatusCode.valueOf(200)
//        );
//    }
}
