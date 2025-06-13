package de.sealcore.util;

import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ResourceManager
{
    /**
     * Reads given resource file as a string.
     *
     * @param fileName path to the resource file
     * @return the file's contents
     * @throws IOException if read fails for any reason
     */
    public static String getResourceFileAsString(String fileName) throws IOException
    {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(fileName))
        {
            if (is == null) return null;
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr))
            {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    public static ImageIcon createImageIcon(String path)
    {
        java.net.URL imgURL = ClassLoader.getSystemClassLoader().getResource(path);
        if (imgURL != null)
        {
            return new ImageIcon(imgURL, "");
        }
        else
        {
            Log.error(LogType.RENDERING, "Couldn't create image icon from " + path);
            return null;
        }
    }
}
