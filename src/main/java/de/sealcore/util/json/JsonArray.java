package de.sealcore.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class JsonArray extends JsonObject implements Iterable<JsonObject>
{
    private final ObjectMapper mapper;
    private ArrayList<JsonObject> objects = new ArrayList<JsonObject>(); //List of json objects


    private JsonArray(String content, ObjectMapper mapper) throws JsonProcessingException
    {
        super(content, mapper);
        this.mapper = mapper;
        objects = getObjects(mapper.readTree(content));
    }

    private JsonArray(File f, ObjectMapper mapper) throws IOException
    {
        super(f, mapper);
        this.mapper = mapper;
        objects = getObjects(mapper.readTree(f));
    }

    protected JsonArray(JsonNode node, ObjectMapper mapper)
    {
        super(node, mapper);
        this.mapper = mapper;
        objects = getObjects(node);
    }

    public static JsonArray fromString(String content)
    {
        //Creates a json array from string with a new mapper
        try
        {
            return new JsonArray(content, new ObjectMapper());
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static JsonArray fromFile(File file)
    {
        //Creates a json object from file with a new mapper
        try
        {
            return new JsonArray(file, new ObjectMapper());
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    private ArrayList<JsonObject> getObjects(JsonNode node)
    {
        //Return a list of all json objects (wrapper object) in a Jackson node
        ArrayList<JsonObject> objects = new ArrayList<JsonObject>();

        for (JsonNode n : node)
        {
            //Returns either array or Object, depending on what the node identifies itself as
            if (n.isArray())
            {
                objects.add(new JsonArray(n, mapper));
            } else
            {
                objects.add(new JsonObject(n, mapper));
            }
        }

        return objects;
    }

    public JsonObject getObject(int index)
    {
        //Return a specific object at the given index
        return objects.get(index);
    }

    public int getInt(String identifier)
    {
        throw new UnsupportedOperationException("Cannot get int from JsonArray");
    }

    public String getString(String identifier)
    {
        throw new UnsupportedOperationException("Cannot get string from JsonArray");

    }

    public boolean getBool(String identifier)
    {
        throw new UnsupportedOperationException("Cannot get bool from JsonArray");

    }

    @Override
    public Iterator<JsonObject> iterator()
    {
        //Allows iteration over the objects in the object list
        return objects.iterator();
    }
}
