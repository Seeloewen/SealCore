package de.sealcore.client.gamestate;

import de.sealcore.game.floors.Grass;
import de.sealcore.game.floors.Stone;
import de.sealcore.game.floors.StoneBricks;
import de.sealcore.game.floors.Water;
import de.sealcore.util.Color;

public class FloorState {

    private Color color;

    FloorState(String id) {
        this(toColor(id));
    }

    FloorState(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    private static Color toColor(String id) {
        return switch(id) {
            case "f:grass" -> new Color(0f,1f,0f);
            case "f:water" -> new Color(0f,0f,1f);
            case "f:stone" -> new Color(0.1f,0.1f,0.1f);
            case "f:stone_bricks" -> new Color(0.69f, 0.69f, 0.69f);
            default -> throw new IllegalStateException("Unexpected value: " + id);
        };
    }

}
