package de.sealcore.client.model;

import de.sealcore.util.Color;

public class Builder {

    Color[] voxels;

    int sizeX;
    int sizeY;
    int sizeZ;

    private int offsetX;
    private int offsetY;
    private int offsetZ;

    int minX;
    int minY;
    int minZ;

    int maxX;
    int maxY;
    int maxZ;


    public Builder(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = Math.min(0, minX);
        this.minY = Math.min(0, minY);
        this.minZ = Math.min(0, minZ);

        sizeX = maxX - this.minX + 1;
        sizeY = maxY - this.minY + 1;
        sizeZ = maxZ - this.minZ + 1;

        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;

        offsetX = -this.minX;
        offsetY = -this.minY;
        offsetZ = -this.minZ;

        voxels = new Color[sizeX * sizeY * sizeZ];

    }

    void set(int x, int y, int z, Color color) {
        voxels[toIndex(x+offsetX, y+offsetY, z+offsetZ)] = color;
    }

    Color get(int x, int y, int z) {
        if(x < minX || y < minY || z < minZ || x > maxX || y > maxY || z > maxZ) return null;
        return voxels[toIndex(x+offsetX, y+offsetY, z+offsetZ)];
    }


    private int toIndex(int x, int y, int z) {
        return x + sizeX*y + sizeX*sizeY*z;
    }








}
