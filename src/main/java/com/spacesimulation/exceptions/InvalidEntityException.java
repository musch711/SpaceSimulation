package com.spacesimulation.exceptions;

public class InvalidEntityException extends Exception
{   
    private String message;
    
    public InvalidEntityException(String s)
    {
        message = s;
    }
    
    @Override
    public String getMessage()
    {
        return message;
    }
    
}
