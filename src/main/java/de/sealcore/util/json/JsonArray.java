package de.sealcore.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class JsonArray extends JsonObject implements Iterable<JsonObject>
{
    protected ArrayNode node;
    private final ObjectMapper mapper;
    private ArrayList<JsonObject> objects = new ArrayList<JsonObject>(); //List of json objects


    private JsonArray(String content, ObjectMapper mapper) throws JsonProcessingException
    {
        super(content, mapper);
        this.mapper = mapper;
        node = (ArrayNode)mapper.readTree(content);
        objects = getObjects(node);
    }

    private JsonArray(File f, ObjectMapper mapper) throws IOException
    {
        super(f, mapper);
        this.mapper = mapper;
        node = (ArrayNode)mapper.readTree(f);
        objects = getObjects(node);
    }

    protected JsonArray(JsonNode node, ObjectMapper mapper)
    {
        super(node, mapper);
        this.mapper = mapper;
        this.node = (ArrayNode)node;
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

    public void addObjectOrArray(JsonObject object)
    {
        //Add object to array node and JsonObject to iterated list
        node.add(object.node);
        objects.add(object);
    }

    @Override
    public int getInt(String identifier)
    {
        throw new UnsupportedOperationException("Cannot get int from JsonArray");
    }

    @Override
    public String getString(String identifier)
    {
        throw new UnsupportedOperationException("Cannot get string from JsonArray");
    }

    @Override
    public boolean getBool(String identifier)
    {
        throw new UnsupportedOperationException("Cannot get bool from JsonArray");
    }

    @Override
    public double getDouble(String identifier)
    {
        throw new UnsupportedOperationException("Cannot get double from JsonArray");
    }

    @Override
    public void addBool(String identifier, boolean value)
    {
        throw new UnsupportedOperationException("Cannot add values to JsonArray");
    }

    @Override
    public void addInt(String identifier, int value)
    {
        throw new UnsupportedOperationException("Cannot add values to JsonArray");
    }

    @Override
    public void addString(String identifier, String value)
    {
        throw new UnsupportedOperationException("Cannot add values to JsonArray");
    }

    @Override
    public void addDouble(String identifier, double value)
    {
        throw new UnsupportedOperationException("Cannot add values to JsonArray");
    }

    @Override
    public void addObject(String identifier, JsonObject value)
    {
        //Redirects to the correct method for arrays
        addObjectOrArray(value);
    }

    public Iterator<JsonObject> iterator()
    {
        //Allows iteration over the objects in the object list
        return objects.iterator();
    }
}
