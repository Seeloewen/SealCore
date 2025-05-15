package de.sealcore.util.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record LogEntry(LogType type, LogLevel level, LocalDateTime date, String message)
{
    public LogEntry(LogType type, LogLevel level, String message)
    {
        this(type, level, LocalDateTime.now(), message);
    }

    public String getMessage()
    {
        //Formatter for time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        return "[" + date.format(formatter) + "] [" + type.toString() + "] [" + level.toString() + "] " + message;
    }
};
