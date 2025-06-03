package de.sealcore.client.model;

import de.sealcore.util.Color;

import java.io.*;
import java.util.ArrayList;

public class Parser {


    public static Builder parse(String path) throws IOException, InvalidFileFormatException {

        ArrayList<Voxel> voxels = new ArrayList<>();

        BufferedReader reader;
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream is = classLoader.getResourceAsStream(path);
        if (is == null) throw new FileNotFoundException();
        InputStreamReader isr = new InputStreamReader(is);
        reader = new BufferedReader(isr);


        int minX = 0, minY = 0, minZ = 0, maxX = 0, maxY = 0, maxZ = 0;
        String currentLine;
        int i = 0;
        while((currentLine = reader.readLine()) != null) {
            i++;
            if(currentLine.charAt(0) == '#') {
                if(currentLine.equals("#END")) break;
            } else {

                String[] values = currentLine.split(" ");
                if(values.length != 4) throw new InvalidFileFormatException(i, "4 values required");

                try {
                    int x = Integer.parseInt(values[0]);
                    int y = Integer.parseInt(values[1]);
                    int z = Integer.parseInt(values[2]);
                    minX = Math.min(minX, x);
                    minY = Math.min(minY, y);
                    minZ = Math.min(minZ, z);
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                    maxZ = Math.max(maxZ, z);

                    String hex = values[3];
                    if (hex == null || !hex.matches("[0-9a-fA-F]{6}")) {
                        throw new InvalidFileFormatException(i, "Invalid hex color format: " + hex);
                    }

                    int r = Integer.parseInt(hex.substring(0, 2), 16);
                    int g = Integer.parseInt(hex.substring(2, 4), 16);
                    int b = Integer.parseInt(hex.substring(4, 6), 16);

                    Color color = new Color(r / 255.0f,g / 255.0f,b / 255.0f);
                    voxels.add(new Voxel(x, y, z, color));
                } catch(NumberFormatException e) {
                    throw new InvalidFileFormatException(i, "coordinates must be integers");
                }
            }

        }

        Builder builder = new Builder(minX, minY, minZ, maxX, maxY, maxZ);
        for(var v : voxels) {
            builder.set(v.x(), v.y(), v.z(), v.color());
        }
        reader.close();
        return builder;
    }








}
