package de.sealcore.game.entities.general;

import java.util.Random;

public class Grassling extends Entity {

    public Grassling() {
        super("e:grassling");
        sizeX = 0.9;
        sizeY = 0.9;
        sizeZ = 2;

        pathFinder = new PathFinder(this);

        moveSpeed = 1;
    }

    public Grassling(double x, double y) {
        super("e:grassling", x, y);
        sizeX = 0.9;
        sizeY = 0.9;
        sizeZ = 2;

        pathFinder = new PathFinder(this);

        Random rnd = new Random();
        moveSpeed = rnd.nextDouble(0.5, 2);
    }

    @Override
    public void doPhysicsTick(double dt)
    {
        pathFinder.doStep();

        super.doPhysicsTick(dt);
    }
}
