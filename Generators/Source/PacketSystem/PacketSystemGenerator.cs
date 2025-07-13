using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp.Syntax;
using Microsoft.CodeAnalysis.Text;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Reflection.Metadata;
using System.Runtime.CompilerServices;
using System.Text;
using System.Xml.Linq;

namespace Generators.PacketSystem
{

    class Packet
    {
        internal string name;
        internal int i = 0;
        internal List<PacketArg> args;
        internal bool server;
    }

    class PacketArg
    {
        internal string name;
        internal string type;
    }


    [Generator]
    public class PacketSystemGenerator : IIncrementalGenerator
    {

        public void Initialize(IncrementalGeneratorInitializationContext context)
        {

            context.RegisterPostInitializationOutput(ctx =>
            {


                const string attributeSource = @"
using System;

namespace SealCore.Networking
{
    [AttributeUsage(AttributeTargets.Class)]
    sealed internal class GeneratePacket : Attribute
    {
        private string domain;
        
        public GeneratePacket(string domain)
        {
            this.domain = domain.ToLower();
        }
    }
}";
                GenerateSourceFile("GeneratePacket", attributeSource, ctx);


            });

            var packets = context.SyntaxProvider.CreateSyntaxProvider(
                predicate: static (node, _) => node is ClassDeclarationSyntax cds && cds.AttributeLists.Count > 0,
                transform: static (ctx, _) =>
                {
                    var classSyntax = (ClassDeclarationSyntax)ctx.Node;
                    var classSymbol = ctx.SemanticModel.GetDeclaredSymbol(classSyntax) as INamedTypeSymbol;

                    foreach (var attr in classSymbol.GetAttributes())
                    {
                        if (attr.AttributeClass?.ToDisplayString() == "SealCore.Networking.GeneratePacket")
                        {

                            var fields = classSymbol
                                    .GetMembers()
                                    .OfType<IFieldSymbol>()
                                    .Where(f => !f.IsStatic)
                                    .ToArray();
                            List<PacketArg> args = new List<PacketArg>();
                            foreach (var field in fields)
                            {
                                PacketArg arg = new PacketArg();
                                arg.name = field.Name;
                                arg.type = field.Type.ToDisplayString();
                                args.Add(arg);
                            }

                            Packet packet = new Packet();
                            packet.name = classSymbol.Name;
                            packet.server = (string)attr.ConstructorArguments[0].Value == "server";
                            packet.args = args;

                            return packet;
                        }
                    }

                    return null;
                }
            ).Where(packet => packet != null);



            context.RegisterSourceOutput(packets.Collect(), static (ctx, packets) =>
            {
                StringBuilder client = new StringBuilder();

                client.AppendLine("using System;");
                client.AppendLine("using SealCore.Util.Json;");
                client.AppendLine();
                client.AppendLine("namespace SealCore.Networking");
                client.AppendLine("{");
                client.AppendLine("    partial class TcpClient");
                client.AppendLine("    {");

                StringBuilder server = new StringBuilder();
                server.AppendLine("using SealCore.Util.Json;");
                server.AppendLine();
                server.AppendLine("namespace SealCore.Networking");
                server.AppendLine("{");
                server.AppendLine("    partial class TcpServer");
                server.AppendLine("    {");

                int i = 0;
                foreach (var packet in packets)
                {
                    packet.i = i++;

                    client.AppendLine();
                    client.AppendLine();
                    server.AppendLine();
                    server.AppendLine();


                    if (!packet.server)
                    {

                        //send method
                        client.Append($"        public static void Send{packet.name}(");

                        bool firstArg = true;
                        foreach (var field in packet.args)
                        {
                            if (firstArg) firstArg = false;
                            else client.Append(", ");

                            client.Append($"{field.type} {field.name}");
                        }

                        client.AppendLine(")");
                        client.AppendLine($"        {{");
                        client.AppendLine($"            {packet.name} packet = new {packet.name}();");

                        foreach (var field in packet.args)
                        {
                            client.AppendLine($"            packet.{field.name} = {field.name};");
                        }
                        client.AppendLine("            instance.send(packet);");

                        client.AppendLine($"        }}");

                        client.AppendLine();
                        client.AppendLine($"        private JsonToken ToJson({packet.name} packet)");
                        client.AppendLine("        {");
                        client.AppendLine("            JsonToken json = new JsonToken();");
                        client.AppendLine($"            json.Set(\"packet_type\", {packet.i});");
                        foreach (var field in packet.args)
                        {
                            client.AppendLine($"            json.Set(\"{field.name}\", packet.{field.name});");
                        }
                        client.AppendLine("            return json;");
                        client.AppendLine("        }");

                        server.AppendLine();

                        server.AppendLine($"        private Packet {packet.name}FromJson(JsonToken token)");
                        server.AppendLine("        {");
                        server.AppendLine($"            {packet.name} packet = new {packet.name}();");
                        foreach (var field in packet.args)
                        {
                            server.AppendLine($"            packet.{field.name} = token.Get<{field.type}>(\"{field.name}\");");
                        }
                        server.AppendLine("            return packet;");
                        server.AppendLine("        }");

                    }
                    else
                    {

                        server.Append($"        public static void Send{packet.name}(");

                        bool firstArg = true;
                        foreach (var field in packet.args)
                        {
                            if (firstArg) firstArg = false;
                            else client.Append(", ");

                            server.Append($"{field.type} {field.name}");
                        }

                        server.AppendLine(")");
                        server.AppendLine($"        {{");
                        server.AppendLine($"            {packet.name} packet = new {packet.name}();");

                        foreach (var field in packet.args)
                        {
                            server.AppendLine($"            packet.{field.name} = {field.name};");
                        }
                        server.AppendLine("            instance.send(packet);");

                        server.AppendLine($"        }}");

                        server.AppendLine();
                        server.AppendLine($"        private JsonToken ToJson({packet.name} packet)");
                        server.AppendLine("        {");
                        server.AppendLine("            JsonToken json = new JsonToken();");
                        server.AppendLine($"            json.Set(\"packet_type\", {packet.i});");
                        foreach (var field in packet.args)
                        {
                            server.AppendLine($"            json.Set(\"{field.name}\", packet.{field.name});");
                        }
                        server.AppendLine("            return json;");
                        server.AppendLine("        }");

                        client.AppendLine($"        private Packet {packet.name}FromJson(JsonToken token)");
                        client.AppendLine("        {");
                        client.AppendLine($"            {packet.name} packet = new {packet.name}();");
                        foreach (var field in packet.args)
                        {
                            client.AppendLine($"            packet.{field.name} = token.Get<{field.type}>(\"{field.name}\");");
                        }
                        client.AppendLine("            return packet;");
                        client.AppendLine("        }");
                    }
                }

                client.AppendLine();
                client.AppendLine();

                client.AppendLine("        private Packet FromPacket(JsonToken token)");
                client.AppendLine("        {");
                client.AppendLine("            return token.Get<int>(\"packet_type\") switch");
                client.AppendLine("            {");
                server.AppendLine();
                server.AppendLine();

                server.AppendLine("        private Packet FromPacket(JsonToken token)");
                server.AppendLine("        {");
                server.AppendLine("            return token.Get<int>(\"packet_type\") switch");
                server.AppendLine("            {");
                foreach (var packet in packets)
                {
                    if (packet.server)
                    {
                        client.AppendLine($"                {packet.i} => {packet.name}FromJson(token),");
                    }
                    else
                    {
                        server.AppendLine($"                {packet.i} => {packet.name}FromJson(token),");
                    }
                }
                client.AppendLine("                _ => null,");
                client.AppendLine("            };");
                client.AppendLine("        }");

                client.AppendLine("    }");
                client.AppendLine("}");

                server.AppendLine("                _ => null,");
                server.AppendLine("            };");
                server.AppendLine("        }");

                server.AppendLine("    }");
                server.AppendLine("}");

                ctx.AddSource($"TcpClient.g.cs", client.ToString());

                ctx.AddSource($"TcpServer.g.cs", server.ToString());
            });





        }


        private void GenerateSourceFile(string name, string content, IncrementalGeneratorPostInitializationContext ctx)
        {
            ctx.AddSource($"{name}.g.cs", SourceText.From(content, Encoding.UTF8));
        }

        private void GenerateSourceFile(string name, string content, SourceProductionContext ctx)
        {
            ctx.AddSource($"{name}.g.cs", SourceText.From(content, Encoding.UTF8));
        }


    }

}
