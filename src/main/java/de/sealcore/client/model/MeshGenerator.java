package de.sealcore.client.model;

import de.sealcore.client.rendering.objects.Mesh;
import de.sealcore.client.rendering.objects.MeshSide;
import de.sealcore.client.rendering.objects.MeshVertex;
import de.sealcore.util.Color;

import java.util.ArrayList;

public class MeshGenerator {

    public static Mesh generate(Builder b, float s) {
        ArrayList<MeshSide> sides = new ArrayList<>();
        for(int x = b.minX; x <= b.maxX; x++) {
            for(int y = b.minY; y <= b.maxY; y++) {
                for(int z = b.minZ; z <= b.maxZ; z++) {
                    Color c = b.get(x, y, z);
                    if(c == null) continue;
                    var v0 = new MeshVertex(x*s, y*s, z*s);
                    var v1 = new MeshVertex(x*s+s, y*s, z*s);
                    var v2 = new MeshVertex(x*s+s, y*s+s, z*s);
                    var v3 = new MeshVertex(x*s, y*s+s, z*s);
                    var v4 = new MeshVertex(x*s, y*s, z*s+s);
                    var v5 = new MeshVertex(x*s+s, y*s, z*s+s);
                    var v6 = new MeshVertex(x*s+s, y*s+s, z*s+s);
                    var v7 = new MeshVertex(x*s, y*s+s, z*s+s);

                    if(b.get(x+1, y, z) == null) {
                        sides.add(new MeshSide(v1, v2, v6, v5, c));
                    }
                    if(b.get(x-1, y, z) == null) {
                        sides.add(new MeshSide(v0, v3, v7, v4, c));
                    }
                    if(b.get(x, y, z+1) == null) {
                        sides.add(new MeshSide(v4, v5, v6, v7, c));
                    }
                    if(b.get(x, y, z-1) == null) {
                        sides.add(new MeshSide(v0, v1, v2, v3, c));
                    }
                    if(b.get(x, y+1, z) == null) {
                        sides.add(new MeshSide(v2, v3, v7, v6, c));
                    }
                    if(b.get(x, y-1, z) == null) {
                        sides.add(new MeshSide(v0, v1, v5, v4, c));
                    }
                }
            }
        }

        return new Mesh(sides.toArray(new MeshSide[0]));

    }

}
