package com.shop.shoes.exception.domain;

public class ExceptionMessage extends RuntimeException{
    public ExceptionMessage(String message){
        super(message);
    }
}
