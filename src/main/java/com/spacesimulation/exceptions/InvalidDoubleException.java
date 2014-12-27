package com.spacesimulation.exceptions;

public class InvalidDoubleException extends Exception
{   
    private String message;
    
    public InvalidDoubleException(String s)
    {
        message = s;
    }
    
    @Override
    public String getMessage()
    {
        return message;
    }
            
}
