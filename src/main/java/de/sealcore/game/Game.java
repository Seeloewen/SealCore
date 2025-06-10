package de.sealcore.game;

import de.sealcore.game.entities.inventory.InventoryManager;
import de.sealcore.game.entities.inventory.InventorySlot;
import de.sealcore.game.maps.Map;
import de.sealcore.game.maps.MapLayout;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.EntityAddPacket;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

import java.util.ArrayList;
import java.util.HashMap;

public class Game
{
    public InventoryManager inventoryManager;
    private ArrayList<Map> maps = new ArrayList<Map>();
    private Map currentMap;


    private ArrayList<Entity> entities;
    public HashMap<Integer, Player> players;




    public void tick(double dt) {

        for(Entity entity : entities) entity.doPhysicsTick(dt);

    }


    public void addPlayer(int id) {
        Player player = new Player(id);
        entities.add(player);
        players.put(id, player);
        player.sendAdd();
        Log.info(LogType.GAME, "player " + id + " joined the game");
    }




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
