package com.fintech.bank.exception;

public class PayeeNotFoundException extends Exception{
    public PayeeNotFoundException(String message){
        super(message);
    }
}
