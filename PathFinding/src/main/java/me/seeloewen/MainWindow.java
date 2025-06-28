package me.seeloewen;

import org.joml.Vector2f;
import org.joml.Vector2i;

import javax.swing.*;

public class MainWindow extends JFrame
{
    public NodeDisplay[] nodes;

    public static int gridWidth;
    public static int gridHeight;
    public static int originX;
    public static int originY;

    Vector2i currentPos;

    public MainWindow(Vector2f startPosition, int radius)
    {
        super("A* Pathfinding");

        currentPos = new Vector2i((int)Math.floor(startPosition.x), (int)Math.floor(startPosition.y));

        gridHeight = Math.abs(radius) * 2;
        gridWidth = Math.abs(radius) * 2;
        originX = currentPos.x - Math.abs(radius);
        originY = currentPos.y - Math.abs(radius);

        setSize(15 + gridWidth * 10, 25 + gridHeight * 10);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        nodes = new NodeDisplay[gridWidth * gridHeight];

        for (int dx = 0; dx < gridWidth; dx++)
        {
            for (int dy = 0; dy < gridHeight; dy++)
            {
                //Create nodes for the pathfinder
                int x = originX + dx;
                int y = originY + dy;

                int i = (-originX + x) + (-originY + y) * gridWidth;
                NodeDisplay n = new NodeDisplay(x, y, NodeState.FREE);
                add(n);

                nodes[i] = n;
            }
        }
    }

    public void update(int x, int y, NodeState state)
    {
        NodeDisplay n = getNode(x, y);

        if(n != null) n.setState(state);
    }

    public NodeDisplay getNode(int x, int y)
    {
        //Create nodes for the pathfinder
        int i = (x - originX) + (y - originY) * gridWidth;

        if (i < 0 || i > nodes.length) return null;
        return nodes[i];
    }
}
