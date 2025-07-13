
using System.Text.Json.Nodes;

namespace SealCore.Util.Json
{
    public class JsonToken
    {
        private JsonObject json;

        public void Set(string key, string value)
        {
            json[key] = value;
        }

        public void Set(string key, int value)
        {
            json[key] = value;
        }

        public T Get<T>(string key)
        {
            return json[key]!.GetValue<T>();
        }

    }
}
