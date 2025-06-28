package de.sealcore.game;

import de.sealcore.client.ui.overlay.CraftingOverlay;
import de.sealcore.game.blocks.BlockRegister;
import de.sealcore.game.chunks.Chunk;
import de.sealcore.game.crafting.CraftingHandler;
import de.sealcore.game.crafting.Recipe;
import de.sealcore.game.entities.general.Entity;
import de.sealcore.game.entities.general.Player;
import de.sealcore.game.entities.inventory.InventoryManager;
import de.sealcore.game.entities.general.*;
import de.sealcore.game.floors.Floor;
import de.sealcore.game.items.TagHandler;
import de.sealcore.game.items.weapons.Pistol;
import de.sealcore.game.items.weapons.Rifle;
import de.sealcore.game.maps.Map;
import de.sealcore.game.maps.MapLayout;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.*;
import de.sealcore.server.Server;
import de.sealcore.server.waves.WaveManager;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

import java.util.ArrayList;
import java.util.HashMap;

import static de.sealcore.util.MathUtil.*;

public class Game
{
    public InventoryManager inventoryManager;
    public CraftingHandler craftingHandler;
    private ArrayList<Map> maps = new ArrayList<Map>();
    private Map currentMap;


    private ArrayList<Entity> entities;
    public HashMap<Integer, Player> players;


    int coreHP = 20;

    public void tick(double dt)
    {
        for(int i = 0; i< entities.size(); i++)
        {
            entities.get(i).doPhysicsTick(dt);
        }
    }

    public void damageCore(int damage) {
        coreHP -= damage;
        if(coreHP <= 0) {
            NetworkHandler.send(new SetHPPacket(0, true));
            NetworkHandler.send(new SetTextPacket("Game over", 2));
            WaveManager.enabled = false;
        } else {
            NetworkHandler.send(new SetHPPacket(coreHP, true));
        }
    }

    public void spawn(Entity e) {
        entities.add(e);
        e.sendAdd();
    }

    public void spawnWave(int grasslings, int bigGrasslings, int jabbus) {
        double spawnRadius = 35;
        for(int i = 0; i < grasslings; i++) {
            double angle = Math.random()*Math.PI*2;
            int x = toBlock(Math.cos(angle)*spawnRadius);
            int y = toBlock(Math.sin(angle)*spawnRadius);
            Floor floor = currentMap.getFloor(x, y);
            if(floor != null && floor.info.isSolid()) {
                spawn(new Grassling(x, y));
            } else {
                i--;
            }
        }
        for(int i = 0; i < bigGrasslings; i++) {
            double angle = Math.random()*Math.PI*2;
            int x = toBlock(Math.cos(angle)*spawnRadius);
            int y = toBlock(Math.sin(angle)*spawnRadius);
            Floor floor = currentMap.getFloor(x, y);
            if(floor != null && floor.info.isSolid()) {
                spawn(new BigGrassling(x, y));
            } else {
                i--;
            }
        }
        for(int i = 0; i < jabbus; i++) {
            double angle = Math.random()*Math.PI*2;
            int x = toBlock(Math.cos(angle)*spawnRadius);
            int y = toBlock(Math.sin(angle)*spawnRadius);
            Floor floor = currentMap.getFloor(x, y);
            if(floor != null && floor.info.isSolid()) {
                spawn(new Jabbus(x, y));
            } else {
                i--;
            }
        }
    }

    public void addPlayer(int id)
    {
        Player player = new Player(id);
        entities.add(player);
        players.put(id, player);
        player.sendAdd();
        NetworkHandler.sendOnly(id, new SetFollowCamPacket(player.getID()));
        Log.info(LogType.GAME, "Player #" + id + " joined the game");
        player.updateLoadedChunks();
        for (Entity entity : entities)
        {
            if (entity != player) entity.sendAdd(id);
        };

        //Sync all recipes over to the client
        for(Recipe r : Server.game.craftingHandler.recipes)
        {
            NetworkHandler.sendOnly(id, new RecipeInitPacket(r.id(), r.output()[0].id(), r.ingredients()));
        }

        player.inventory.add(5, "i:sword", 1);
        player.inventory.add(0, "i:axe", 1);
        player.inventory.add(1, "i:pickaxe", 1);
        NetworkHandler.sendOnly(id, new SetHPPacket(coreHP, true));
    }

    public void removePlayer(int clientID) {
        Entity e = players.get(clientID);
        removeEntity(e.getID());
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

        //Create core
        getCurrentMap().getChunk(0,0).setBlock(0,0, BlockRegister.getBlock("b:core_1"));
        getCurrentMap().getChunk(-1,0).setBlock(7,0, BlockRegister.getBlock("b:core_4"));
        getCurrentMap().getChunk(0,-1).setBlock(0,7, BlockRegister.getBlock("b:core_2"));
        getCurrentMap().getChunk(-1,-1).setBlock(7,7, BlockRegister.getBlock("b:core_3"));

        /*Random rnd = new Random();
        for (int i = 0; i < 10; i++)
        {
            var e = new Grassling(rnd.nextDouble(-20, 20), rnd.nextDouble(-20, 20));
            entities.add(e);
            e.sendAdd();
        }*/
    }

    public void removeEntity(int id) {
        for(int i = 0; i < entities.size(); i++) {
            if(entities.get(i).getID() == id) {
                entities.remove(i);
                NetworkHandler.send(new EntityRemovePacket(id));
                return;
            }
        }
    }

    public Entity getEntity(int id) {
        for(var e : entities) {
            if(e.getID() == id) return e;
        }
        return null;
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
        Chunk chunk = currentMap.getChunk(toChunk(x), toChunk(y));
        //return false;
        if (chunk == null) return false;
        var block = chunk.getBlock(safeMod(x, 8), safeMod(y, 8));
        return !chunk.getFloor(safeMod(x, 8), safeMod(y, 8)).info.isSolid() || (block != null && block.info.isSolid());
    }

}
