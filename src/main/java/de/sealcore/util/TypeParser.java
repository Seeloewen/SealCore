package de.sealcore.util;

public class TypeParser
{
    public static boolean isInt(String s)
    {
        try
        {
            Integer.parseInt(s); //Try to parse Input, if no exception is thrown it will return true
            return true;
        }
        catch(Exception ex)
        {
            return false; //If exception thrown, we can assume it's not an int
        }
    }

    public static boolean isDouble(String s)
    {
        try
        {
            Double.parseDouble(s); //Try to parse Input, if no exception is thrown it will return true
            return true;
        }
        catch(Exception ex)
        {
            return false; //If exception thrown, we can assume it's not an int
        }
    }

    public static boolean isBool(String s)
    {
        //Checks if it's either true or false
        return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
    }

    public static int getInt(String s)
    {
        //Returns 0 by default. To avoid mistaking this 0 as
        //the actual result of the parseInput, it's recommended to check first if it's parseable
        try
        {
            return Integer.parseInt(s);
        }
        catch(Exception ex)
        {
            return 0;
        }
    }

    public static double getDouble(String s)
    {
        //Returns 0 by default. To avoid mistaking this 0 as
        //the actual result of the parseInput, it's recommended to check first if it's parseable
        try
        {
            return Double.parseDouble(s);
        }
        catch(Exception ex)
        {
            return 0;
        }
    }

    public static boolean getBool(String s)
    {
        return Boolean.getBoolean(s);
    }
}
