package de.sealcore.client.input;

import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.MoveInputPacket;

public record PlayerMoveInputState (int x, int y, boolean sprint) {

    static private PlayerMoveInputState state;

    public static void update() {
        if(state == null) state = generate();
        var newState = generate();
        if(!state.equals(newState)) newState.send();
        state = newState;
    }


    private static PlayerMoveInputState generate() {
        int x = 0, y = 0;
        if(InputHandler.isPressed(KeyBinds.PLAYER_MOVE_RIGHT)) y--;
        if(InputHandler.isPressed(KeyBinds.PLAYER_MOVE_LEFT)) y++;
        if(InputHandler.isPressed(KeyBinds.PLAYER_MOVE_UP)) x++;
        if(InputHandler.isPressed(KeyBinds.PLAYER_MOVE_DOWN)) x--;
        return new PlayerMoveInputState(x, y, InputHandler.isPressed(KeyBinds.PLAYER_SPRINT));
    }

    private void send() {
        NetworkHandler.send(new MoveInputPacket(x, y));
    }



}
