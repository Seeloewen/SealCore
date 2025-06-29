package de.sealcore.game.entities.general.pathfinding;

public class Node implements Comparable<Node>
{
    public Node predecessor;
    public NodeState state = NodeState.FREE;

    public final double x;
    public final double y;
    public int g = 0;
    public double f = 0;

    public Node(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Node other)
    {
        return Double.compare(this.f, other.f);
    }

    @Override
    public int hashCode()
    {
        int prime = 31;
        int result = 1;
        result = prime * result + (int)Math.floor(x);
        result = prime * result + (int)Math.floor(y);
        return result;
    }


    @Override
    public boolean equals(Object o)
    {
        if (o == this) return true;

        if (o instanceof Node n)
        {
            return n.x == x && n.y == y;
        }

        return false;
    }
}
