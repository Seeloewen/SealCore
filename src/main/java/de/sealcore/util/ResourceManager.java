package de.sealcore.util;

import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

import javax.swing.*;
import java.io.*;
import java.net.URL;
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
        fileName = "/" + fileName;

        try (InputStream is = ResourceManager.class.getResourceAsStream(fileName))
        {
            if (is == null) throw new FileNotFoundException("Resource not found: " + fileName);
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
        path = "/" + path;

        URL imgURL = ResourceManager.class.getResource(path);
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
