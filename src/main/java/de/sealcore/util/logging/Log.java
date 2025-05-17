package de.sealcore.util.logging;

import java.util.ArrayList;

public class Log
{
    public static ArrayList<LogEntry> entries = new ArrayList<LogEntry>();

    public static void write(LogType type, LogLevel level, String message)
    {
        //Add entry to list and write to console
        LogEntry e = new LogEntry(type, level, message);
        entries.add(e);
        System.out.println(e.getMessage());
    }

    public static void info(LogType type, String message)
    {
        write(type, LogLevel.INFO, message);
    }

    public static void warn(LogType type, String message)
    {
        write(type, LogLevel.WARNING, message);
    }

    public static void error(LogType type, String message)
    {
        write(type, LogLevel.ERROR, message);
    }

    public static void debug(String message)
    {
        write(LogType.MAIN, LogLevel.DEBUG, message);
    }

    public static String getLine(int index)
    {
        return entries.get(index).getMessage();
    }

    public static String getContent()
    {
        StringBuilder logContentB = new StringBuilder();

        //Construct content from entries
        for (LogEntry entry : entries)
        {
            logContentB.append(entry.getMessage() + "\n");
        }

        return logContentB.toString();
    }
}
