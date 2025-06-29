package de.sealcore.game.entities.general;

import de.sealcore.game.entities.general.pathfinding.PathFinder;
import de.sealcore.server.Server;

import java.util.Random;

public class Jabbus extends Entity
{
    final int DAMAGE = 7;
    final double RANGE_SQR = 3 * 3;
    final double COOLDOWN = 2;

    double attackCooldown = 0;

    public Jabbus(double x, double y)
    {
        super("e:jabbus", 25, x, y);
        sizeX = 0.95;
        sizeY = 0.95;
        sizeZ = 3;

        pathFinder = new PathFinder(this);

        Random rnd = new Random();
        moveSpeed = rnd.nextDouble(0.5, 1.5);
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
        Server.game.players.get(source).inventory.add("i:rock", 10);
    }
}
