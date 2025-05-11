package de.sealcore.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonObject
{
    private JsonNode node; //The actual json object as defined by Jackson lib
    private ObjectMapper mapper;

    protected JsonObject(String content, ObjectMapper mapper) throws JsonProcessingException
    {
        this.mapper = mapper;
        node = mapper.readTree(content);
    }

    protected JsonObject(File f, ObjectMapper mapper) throws IOException
    {
        this.mapper = mapper;
        node = mapper.readTree(f);
    }

    protected JsonObject(JsonNode node, ObjectMapper mapper)
    {
        this.mapper = mapper;
        this.node = node;
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
        //Gets the value from the identifier and try's to parse it, assuming it's an int. If not, an exception get's thrown
        return Integer.parseInt(node.get(identifier).toString());
    }

    public String getString(String identifier)
    {
        //Gets the value from the identifier and removes the quotation marks, as they are part of the value for some reason
        return node.get(identifier).toString().replace("\"", "");
    }

    public boolean getBool(String identifier)
    {
        //Gets the value from the identifier and try's to convert it to a boolean. Returns false if no good value is given
        return Boolean.getBoolean(node.get(identifier).toString());
    }

    @Override
    public String toString()
    {
        //Outputs the entire object
        return node.toString();
    }
}
