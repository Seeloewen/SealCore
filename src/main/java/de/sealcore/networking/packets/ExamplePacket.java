package de.sealcore.networking.packets;

public class ExamplePacket extends Packet
{
    String s;
    int i;

    private ExamplePacket(String s, int i)
    {
        super(PacketType.EXAMPLE);

        this.s = s;
        this.i = i;
    }

    public Packet fromJson(String json)
    {
        //TODO: parse attributes from json object

        return new ExamplePacket("", 0);
    }

    public String toJson()
    {
        //TODO: create json string from attributes

        return "";
    }

    public void handle()
    {
        //Do something
    }
}
