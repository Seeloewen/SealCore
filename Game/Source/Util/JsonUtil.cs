
using System.Text.Json.Nodes;

namespace SealCore.Util.Json
{
    public class JsonToken
    {
        private JsonObject json;


        public JsonToken()
        {
            json = new JsonObject();
        }

        private JsonToken(JsonObject json)
        {
            this.json = json;
        }

        public JsonToken(string jsonString)
        {
            json = (JsonObject)JsonObject.Parse(jsonString);
        }
        
        
        public void Set(string key, string value)
        {
            json[key] = value;
        }

        public void Set(string key, int value)
        {
            json[key] = value;
        }

        public void Set(string key, bool value)
        {
            json[key] = value;
        }

        public void Set(string key, float value)
        {
            json[key] = value;
        }

        public void Set(string key, double value)
        {
            json[key] = value;
        }

        
        public T GetValue<T>(string key)
        {
            return json[key]!.GetValue<T>();
        }

        public JsonToken GetToken(string key)
        {
             return new JsonToken(json[key]!.GetValue<JsonObject>());
        }
        
        
        
        
        
        
    }
}
