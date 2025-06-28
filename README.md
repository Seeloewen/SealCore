# SealCore
SealCore is a private project made for a school assignement by @Seeloewen (Louis) and @CDLemmi (Christopher). It is meant for learning purposes only.
Development may be inconsistant and could stop entirely at some point.

# Game Concept
The main goal of the game is to protect the core in the center of the world. Over the time, waves of monsters will spawn that try to destory the core. You can gather materials in the randomly generated world around you to craft weapons and bullets to defend yourself. How long can you survive?

# Setup
To start a game, you will need to start an instance of a server for the clients to connect to. 
The port is 5000 by default, unless rerouted. If everyone is connected and you're reeady you need to execute **/start** in the server console to start the game.


# Screenshots

![image](https://github.com/user-attachments/assets/6f1dd425-91da-485d-8b13-57c45d8df7f9)

![image](https://github.com/user-attachments/assets/8ea57047-1221-48aa-87b9-4574e4c9f804)

![image](https://github.com/user-attachments/assets/9190e5d4-6ec2-4ad8-b6be-035a117f3217)

# Building
If you want to build the project, clone or download the code and open it in IntelliJ (preferably). To run it, execute **gradlew build**, followed by **gradlew run --args="s"** for the server and **gradlew run --args="c"** for the client. Running **gradlew build** will also yield a fatjar in the build/libs folder that can be used standalone.

# Credits
**Third-Party libraries used:**\
LWJGL (https://github.com/LWJGL/lwjgl3) \
JOML (https://github.com/JOML-CI/JOML) \
Jackson-Databind (https://github.com/FasterXML/jackson-databind) \
FlatLaf (https://github.com/JFormDesigner/FlatLaf)
