package de.sealcore.game;

import de.sealcore.game.chunks.Chunk;
import de.sealcore.game.crafting.CraftingHandler;
import de.sealcore.game.entities.general.Entity;
import de.sealcore.game.entities.general.Player;
import de.sealcore.game.entities.inventory.InventoryManager;
import de.sealcore.game.entities.inventory.InventorySlot;
import de.sealcore.game.entities.general.*;
import de.sealcore.game.maps.Map;
import de.sealcore.game.maps.MapLayout;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.ChunkAddPacket;
import de.sealcore.networking.packets.EntityAddPacket;
import de.sealcore.networking.packets.SetFollowCamPacket;
import de.sealcore.server.Server;
import de.sealcore.util.ChunkIndex;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class Game
{
    public InventoryManager inventoryManager;
    public CraftingHandler craftingHandler;
    private ArrayList<Map> maps = new ArrayList<Map>();
    private Map currentMap;


    private ArrayList<Entity> entities;
    public HashMap<Integer, Player> players;


    public void tick(double dt)
    {

        for (Entity entity : entities) entity.doPhysicsTick(dt);

    }


    public void addPlayer(int id)
    {
        Player player = new Player(id);
        entities.add(player);
        players.put(id, player);
        NetworkHandler.sendOnly(id, new SetFollowCamPacket(player.getID()));
        player.sendAdd();
        Log.info(LogType.GAME, "player " + id + " joined the game");
        for (int i = 0; i < 32; i++)
        {
            var chunk = currentMap.getChunk(i);
            if (chunk != null) chunk.sendAddPacket(id);
        }
        for (Entity entity : entities)
        {
            if (entity != player) entity.sendAdd(id);
        }
    }


    public Game()
    {
        entities = new ArrayList<>();
        players = new HashMap<>();

        inventoryManager = new InventoryManager();
        craftingHandler = new CraftingHandler();

        //Create initial map
        addMap(nextMapId(), MapLayout.NORMAL);
        loadMap(0);

        var e = new Grassling(0, 0);
        entities.add(e);
        e.sendAdd();

        /*Random rnd = new Random();
        for (int i = 0; i < 10; i++)
        {
            var e = new Grassling(rnd.nextDouble(-10, 10), rnd.nextDouble(-10, 10));
            entities.add(e);
            e.sendAdd();
        }*/
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

    public boolean isSolid(int x, int y)
    {
        Chunk chunk = currentMap.getChunk(x < 0 ? (x - 7) / 8 : x / 8, y < 0 ? (y - 7) / 8 : y / 8);
        //return false;
        if (chunk == null) return false;
        var block = chunk.getBlock((x % 8 + 8) % 8, (y % 8 + 8) % 8);
        return !chunk.getFloor((x % 8 + 8) % 8, (y % 8 + 8) % 8).info.isSolid() || (block != null && block.info.isSolid());
    }

}
