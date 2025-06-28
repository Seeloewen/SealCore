package de.sealcore.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class JsonObject
{
    protected final ObjectNode node; //The actual json object as defined by Jackson lib
    private final ObjectMapper mapper;

    protected JsonObject(String content, ObjectMapper mapper) throws JsonProcessingException
    {
        this.mapper = mapper;
        node = (ObjectNode)mapper.readTree(content);
    }

    protected JsonObject(File f, ObjectMapper mapper) throws IOException
    {
        this.mapper = mapper;
        node = (ObjectNode)mapper.readTree(f);
    }

    protected JsonObject(JsonNode node, ObjectMapper mapper)
    {
        this.mapper = mapper;
        this.node = (ObjectNode)node;
    }

    protected JsonObject(ObjectMapper mapper)
    {
        node = mapper.createObjectNode();
        this.mapper = mapper;
    }

    public static JsonObject fromString(String content)
    {
        //Creates a json object from string with a new mapper
        try
        {
            return new JsonObject(content, new ObjectMapper());
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static JsonObject fromFile(File file)
    {
        //Creates a json object from file with a new mapper
        try
        {
            return new JsonObject(file, new ObjectMapper());
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static JsonObject fromScratch()
    {
        //Creates a new json object with a new mapper
        try
        {
            return new JsonObject(new ObjectMapper());
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public JsonObject getObject(String identifier)
    {
        //Try to get an object from the given identifier, may return null
        //This can also be used to get arrays, however a cast will be needed to use them as intended
        return new JsonObject(node.get(identifier), mapper);
    }

    public JsonArray getArray(String identifier)
    {
        //Try to get an array from the given identifier, may return null
        return new JsonArray(node.get(identifier), mapper);
    }

    public int getInt(String identifier)
    {
        //Gets the value from the identifier and try's to parseInput it, assuming it's an int. If not, an exception get's thrown
        return node.get(identifier).asInt(0);
    }

    public String getString(String identifier)
    {
        //Gets the value from the identifier
        return node.get(identifier).asText();
    }

    public double getDouble(String identifier)
    {
        //Gets the value from the identifier
        return node.get(identifier).asDouble(0);
    }

    public boolean getBool(String identifier)
    {
        //Gets the value from the identifier and try's to convert it to a boolean. Returns false if no good value is given
        return node.get(identifier).asBoolean(false);
    }

    public void addBool(String identifier, boolean value)
    {
        node.put(identifier, value);
    }

    public void addInt(String identifier, int value)
    {
        node.put(identifier, value);
    }

    public void addString(String identifier, String value)
    {
        node.put(identifier, value);
    }

    public void addDouble(String identifier, double value)
    {
        node.put(identifier, value);
    }

    public void addObject(String identifier, JsonObject object)
    {
        node.set(identifier, object.node);
    }

    public void addArray(String identifier, JsonArray array)
    {
        node.set(identifier, array.node);
    }

    @Override
    public String toString()
    {
        //Outputs the entire object
        return node.toString();
    }
}
