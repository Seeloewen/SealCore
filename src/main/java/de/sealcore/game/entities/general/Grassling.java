package de.sealcore.game.entities.general;

import java.util.Random;

public class Grassling extends Entity {

    final int DAMAGE = 2;
    final double RANGE_SQR = 2*2;
    final double COOLDOWN = 1;

    double attackCooldown = 0;

    public Grassling(double x, double y) {
        super("e:grassling", 10, x, y);
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

        var p = pathFinder.playerTarget;
        if(p != null && attackCooldown <= 0) {
            var dx = p.posX-posX;
            var dy = p.posY-posY;
            if(dx*dx+dy*dy <= RANGE_SQR) {
                p.damage(DAMAGE, getID());
                attackCooldown = COOLDOWN;
            }
        }
        attackCooldown -= dt;

        super.doPhysicsTick(dt);
    }
}
