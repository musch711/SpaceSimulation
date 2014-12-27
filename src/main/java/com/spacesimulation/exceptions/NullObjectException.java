package com.spacesimulation.exceptions;

public class NullObjectException extends Exception
{
    private String message;
    
    public NullObjectException(String s)
    {
        message = s;
    }
    
    @Override
    public String getMessage()
    {
        return message;
    }
}
