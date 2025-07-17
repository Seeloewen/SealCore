package de.sealcore.game.entities.general;

import de.sealcore.server.Server;

import java.util.Random;

public class Grassling extends Entity {

    final int DAMAGE = 2;
    final double RANGE_SQR = 2*2;
    final double COOLDOWN = 1;

    double attackCooldown = 0;

    public Grassling(double x, double y) {
        super("e:grassling", "Grassling", 10, x, y);
        sizeX = 0.7;
        sizeY = 0.7;
        sizeZ = 2;

        pathFinder = new PathFinder(this);

        Random rnd = new Random();
        moveSpeed = rnd.nextDouble(0.5, 2);
    }

    @Override
    public void doPhysicsTick(double dt)
    {
        pathFinder.doStep();

        if (attackCooldown <= 0) {
            var p = pathFinder.playerTarget;
            if(p != null) {
                var dx = p.posX-posX;
                var dy = p.posY-posY;
                if(dx*dx+dy*dy <= RANGE_SQR) {
                    p.damage(DAMAGE, getID());
                    attackCooldown = COOLDOWN;
                }
            }

            var dx = posX;
            var dy = posY;
            if(dx*dx+dy*dy <= 10) {
                Server.game.damageCore(DAMAGE);
                attackCooldown = COOLDOWN;
            }

        }
        attackCooldown -= dt;

        super.doPhysicsTick(dt);
    }

    @Override
    protected void onDeath(int source)
    {
        super.onDeath(source);
        Server.game.players.get(source).inventory.add("i:rock", 1);
    }
}
