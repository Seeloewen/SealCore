package de.sealcore.util;

public class TypeParser
{
    public boolean IsInt(String s)
    {
        try
        {
            Integer.parseInt(s); //Try to parse, if no exception is thrown it will return true
            return true;
        }
        catch(Exception ex)
        {
            return false; //If exception thrown, we can assume it's not an int
        }
    }

    public boolean IsBool(String s)
    {
        //Checks if it's either true or false
        return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
    }

    public int GetInt(String s)
    {
        //Returns 0 by default. To avoid mistaking this 0 as
        //the actual result of the parse, it's recommended to check first if it's parseable
        try
        {
            return Integer.parseInt(s);
        }
        catch(Exception ex)
        {
            return 0;
        }
    }

    public boolean GetBool(String s)
    {
        return Boolean.getBoolean(s);
    }
}
