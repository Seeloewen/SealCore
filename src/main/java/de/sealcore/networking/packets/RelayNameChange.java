package de.sealcore.networking.packets;

import de.sealcore.client.Client;
import de.sealcore.server.Server;
import de.sealcore.util.json.JsonObject;

public class RelayNameChange extends Packet
{
    private int id;
    private String displayName;

    public RelayNameChange(int id, String displayName)
    {
        super(PacketType.RELAYNAMECHANGE);

        this.id = id;
        this.displayName = displayName;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        int id = args.getInt("id");
        String displayName = args.getString("display_name");

        return new RelayNameChange(id, displayName);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        args.addInt("id", id);
        args.addString("display_name", displayName);

        obj.addObject("args", args);

        return obj.toString();
    }

    public void onHandle()
    {
        Client.instance.gameState.loadedMeshes.get(id).displayName = displayName;
    }
}
