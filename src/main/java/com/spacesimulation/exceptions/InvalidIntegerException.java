package com.spacesimulation.exceptions;

public class InvalidIntegerException extends Exception
{
    private String message;
    
    public InvalidIntegerException(String s)
    {
        message = s;
    }
    
    @Override
    public String getMessage()
    {
        return message;
    }
}
