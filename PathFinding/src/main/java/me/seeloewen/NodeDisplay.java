package me.seeloewen;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;

public class NodeDisplay extends JComponent
{
    private NodeState state = NodeState.FREE;
    public final int x;
    public final int y;

    public NodeDisplay(int x, int y, NodeState state)
    {
        this.x = x;
        this.y = y;
        this.state = state;

        int screenX = (x - MainWindow.originX) * 10;
        int screenY = (MainWindow.gridHeight - 1 - (y - MainWindow.originY)) * 10;
        setLocation(screenX, screenY);


        //setLocation(x * 10 + -MainWindow.originX * 10, 25 + PathFinder.gridHeight * 10 - y * 10 + MainWindow.originY * 10);
        setSize(10, 10);
    }

    public void setState(NodeState state)
    {
        this.state = state;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Color c = null;
        switch(state)
        {
            case FREE -> c = Color.GRAY;
            case OCCUPIED -> c = Color.darkGray;
            case OPTIMAL -> c = Color.green;
            case VISITED -> c = Color.blue;
        }

        g.setColor(c);

        g.fillRect(0, 0, 10, 10);
        g.drawRect(0, 0, 10, 10);
    }
}
