import JsonUtil.JsonArray;
import JsonUtil.JsonObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        System.out.println("Wello Horld");

        File file = new File("G:\\inventory_5546546.json");

        for (JsonObject o : JsonObject.fromFile(file).getArray("slots"))
        {
            System.out.println(o.getObject("item").getString("tag"));
        }
    }
}
