//package com.dkasiian.controllers.exceptions;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class ExceptionHandlerUtil {
//
//    @ExceptionHandler
//    public ResponseEntity<ErrorResponse> handleException(NotFoundException exception){
//        ErrorResponse response = new ErrorResponse(
//                HttpStatus.NOT_FOUND.value(),
//                exception.getMessage() + ", " + HttpStatus.NOT_FOUND,
//                System.currentTimeMillis()
//        );
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<ErrorResponse> handleException(DuplicateSubmitException exception){
//        ErrorResponse response = new ErrorResponse(
//                HttpStatus.CONFLICT.value(),
//                exception.getMessage() + ", " + HttpStatus.CONFLICT,
//                System.currentTimeMillis()
//        );
//        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<ErrorResponse> handleException(Exception exception){
//        ErrorResponse response = new ErrorResponse(
//                HttpStatus.BAD_REQUEST.value(),
//                exception.getMessage() + ", " + HttpStatus.BAD_REQUEST,
//                System.currentTimeMillis()
//        );
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//}
