package de.sealcore.game;

public class InteractableObject
{
    public int onLeftClick(int playerId)
    {
        //Returns -1 if not implemented. Should return 0 if implemented to avoid unwanted behaviour!
        return -1;
    }

    public int onRightClick(int playerId)
    {
        //Returns -1 if not implemented. Should return 0 if implemented to avoid unwanted behaviour!
        return -1;
    }

    public void onCollision()
    {
        //Maybe needed, maybe not
    }
}
