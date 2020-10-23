package com.fintech.bank.exception;

public class InvalidAccountException extends Exception{
    public InvalidAccountException(String message){
        super(message);
    }
}
