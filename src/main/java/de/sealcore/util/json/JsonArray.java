package de.sealcore.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import de.sealcore.util.logging.Log;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class JsonArray extends JsonObject implements Iterable<Object>
{
    protected ArrayNode node;
    private final ObjectMapper mapper;
    private ArrayList<Object> objects = new ArrayList<Object>(); //List of json objects


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
        super(mapper);
        this.mapper = mapper;
        this.node = (ArrayNode)node;
        objects = getObjects(node);
    }

    protected JsonArray(ObjectMapper mapper)
    {
        super(mapper);
        this.mapper = mapper;
        this.node = mapper.createArrayNode();
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

    public static JsonArray fromScratch()
    {
        //Creates a json object from file with a new mapper
        try
        {
            return new JsonArray(new ObjectMapper());
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    private ArrayList<Object> getObjects(JsonNode node)
    {
        //Return a list of all json objects (wrapper object) in a Jackson node
            ArrayList<Object> objects = new ArrayList<Object>();

        for (JsonNode n : node)
        {
            //Adds either array or Object or primitive type, depending on what the node identifies itself as
            if (n.isArray())
            {
                objects.add(new JsonArray(n, mapper));
            }
            else if(n.isObject())
            {
                objects.add(new JsonObject(n, mapper));
            }
            else
            {
                objects.add(n);
            }
        }

        return objects;
    }

    public Object get(int index)
    {
        //Return a specific object at the given index
        return objects.get(index);
    }

    public void addObject(JsonObject obj)
    {
        //Add object to array node and JsonObject to iterated list
        node.add(obj.node);
        objects.add(obj);
    }

    public void addArray(JsonArray arr)
    {
        //Add object to array node and JsonObject to iterated list
        node.add(arr.node);
        objects.add(arr);
    }

    public void addInt(int i)
    {
        //Add object to array node and JsonObject to iterated list
        node.add(i);
        objects.add(i);
    }

    public void addDouble(double d)
    {
        //Add object to array node and JsonObject to iterated list
        node.add(d);
        objects.add(d);
    }

    public void addBool(boolean b)
    {
        //Add object to array node and JsonObject to iterated list
        node.add(b);
        objects.add(b);
    }

    public void addString(String s)
    {
        //Add object to array node and JsonObject to iterated list
        node.add(s);
        objects.add(s);
    }

    @NotNull
    public Iterator<Object> iterator()
    {
        //Allows iteration over the objects in the object list
        return objects.iterator();
    }

    /* The following part contains redirects to the correct methods
       While the ones below can be used, it's an unnecessary extra parameter
       and can lead to confusion when the identifier is missing in the end
     */

    @Override @Deprecated
    public void addObject(String identifier, JsonObject obj)
    {
        addObject(obj);
    }

    @Override @Deprecated
    public void addArray(String identifier, JsonArray arr)
    {
        addObject(arr);
    }

    @Override @Deprecated
    public void addInt(String identifier, int i)
    {
        addInt(i);
    }

    @Override @Deprecated
    public void addString(String identifier, String s)
    {
        addString(s);
    }

    @Override @Deprecated
    public void addDouble(String identifier, double d)
    {
        addDouble(d);
    }

    @Override
    public void addBool(String identifier, boolean b)
    {
        addBool(b);
    }
}
