package de.sealcore.game;

import de.sealcore.game.entities.inventory.InventoryManager;
import de.sealcore.game.maps.Map;
import de.sealcore.game.maps.MapLayout;

import java.util.ArrayList;

public class Game
{
    public InventoryManager inventoryManager;
    private ArrayList<Map> maps = new ArrayList<Map>();
    private Map currentMap;

    public Game()
    {
        inventoryManager = new InventoryManager();

        //Create initial map
        addMap(nextMapId(), MapLayout.NORMAL);
        loadMap(0);
    }

    public void addMap(int id, MapLayout layout)
    {
        maps.add(new Map(id, layout));
    }

    public void loadMap(int id)
    {
        currentMap = maps.get(id);
    }

    public Map getCurrentMap()
    {
        return currentMap;
    }

    public int nextMapId()
    {
        return maps.size();
    }
}
