package com.spyj.estore.ControllerAdvices;

import com.spyj.estore.DTOs.ArithmeticExceptionDto;
import com.spyj.estore.DTOs.ExceptionDto;
import com.spyj.estore.Exceptions.CategoryNotFoundException;
import com.spyj.estore.Exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleProductNotFoundException(ProductNotFoundException productNotFoundException){
        ExceptionDto exceptionDto = new ExceptionDto(
                productNotFoundException.getMessage(),
                "404",
                "Not Found",
                Instant.now().toString(),
                Arrays.toString(productNotFoundException.getStackTrace())
        );
        Logger.getLogger("Trace : " + exceptionDto.getTrace());
        return new ResponseEntity<>(
                exceptionDto, HttpStatusCode.valueOf(404)
        );
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleCategoryNotFoundException(CategoryNotFoundException categoryNotFoundException){
        ExceptionDto exceptionDto = new ExceptionDto(
                categoryNotFoundException.getMessage(),
                "404",
                "Not Found",
                Instant.now().toString(),
                Arrays.toString(categoryNotFoundException.getStackTrace())
        );
        Logger.getLogger("Trace : " + exceptionDto.getTrace());
        return new ResponseEntity<>(
                exceptionDto, HttpStatusCode.valueOf(404)
        );
    }

//    @ExceptionHandler(ArithmeticException.class)
//    public ResponseEntity<ArithmeticExceptionDto> handleArithmeticException(){
//        ArithmeticExceptionDto arithmeticExceptionDto = ArithmeticExceptionDto.builder().message("Illegal Argument passed.").build();
//        return new ResponseEntity<>(
//                arithmeticExceptionDto, HttpStatusCode.valueOf(200)
//        );
//    }
}
