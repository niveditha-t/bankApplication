package com.fintech.bank.exception;

public class InvalidTransactionException extends Exception{
    public InvalidTransactionException(String message){
        super(message);
    }
}
