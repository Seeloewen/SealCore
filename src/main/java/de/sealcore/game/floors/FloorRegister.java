package de.sealcore.game.floors;

public class FloorRegister
{
    public static Floor getFloor(String id, int height)
    {
        //Go through the register and return a new instance of the specified floor
        return switch(id)
        {
            case "f:grass" -> new Grass(height);
            case "f:water" -> new Water(height);
            default -> null;
        };
    }
}
