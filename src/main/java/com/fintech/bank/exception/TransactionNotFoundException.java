package com.fintech.bank.exception;

public class TransactionNotFoundException extends Exception{
    public TransactionNotFoundException(String message){
        super(message);
    }
}
