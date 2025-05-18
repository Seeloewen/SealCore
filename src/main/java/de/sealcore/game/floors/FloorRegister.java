package de.sealcore.game.floors;

public class FloorRegister
{
    public static Floor genFloor(String id)
    {
        //Go through the register and return a new instance of the specified floor
        switch(id)
        {
            case "f:ground" -> new Ground();
            case "f:water" -> new Water();
        }

        return null;
    }
}
