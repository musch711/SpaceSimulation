package com.spacesimulation.exceptions;

public class ColorNotFoundException extends Exception
{
    private String message;
    
    public ColorNotFoundException(String m)
    {
        message = m;
    }
    
    @Override
    public String getMessage()
    {
        return message;
    }
}
