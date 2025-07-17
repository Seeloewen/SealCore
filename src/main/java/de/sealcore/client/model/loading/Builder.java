package de.sealcore.client.model.loading;

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

    Builder genLOD() {
        Builder out = new Builder((minX-1)/2, (minY-1)/2, (minZ-1)/2, (maxX)/2, (maxY)/2, (maxZ)/2);
        for(int xOut = out.minX; xOut <= out.maxX; xOut++) {
            for(int yOut = out.minY; yOut <= out.maxY; yOut++) {
                for(int zOut = out.minZ; zOut <= out.maxZ; zOut++) {
                    int x1 = 2*xOut;
                    int y1 = 2*yOut;
                    int z1 = 2*zOut;
                    int x2 = x1+1;
                    int y2 = y1+1;
                    int z2 = z1+1;
                    float totalR = 0, totalG = 0, totalB = 0;
                    int count = 0;
                    for(int x = x1; x <= x2; x++) {
                        for(int y = y1; y <= y2; y++) {
                            for(int z = z1; z <= z2; z++) {
                                Color c = get(x, y, z);
                                if(c != null && isVoxelOutside(x, y, z)){
                                    totalR += c.r();
                                    totalG += c.g();
                                    totalB += c.b();
                                    count++;
                                }
                            }
                        }
                    }
                    if(count >= 4) {
                        out.set(xOut, yOut, zOut, new Color(totalR/count, totalG/count, totalB/count));
                    }
                }
            }
        }
        return out;
    }


    private int toIndex(int x, int y, int z) {
        return x + sizeX*y + sizeX*sizeY*z;
    }

    private boolean isVoxelOutside(int x, int y, int z) {
        return get(x+1, y, z) == null
                || get(x-1, y, z) == null
                || get(x, y+1, z) == null
                || get(x, y-1, z) == null
                || get(x, y, z-1) == null
                || get(x, y, z+1) == null;
    }






}
