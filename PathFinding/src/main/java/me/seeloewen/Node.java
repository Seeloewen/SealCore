package me.seeloewen;

public class Node implements Comparable<Node>
{
    public Node predecessor;
    public NodeState state = NodeState.FREE;

    public int gridWidth;
    public final int x;
    public final int y;
    public int g = 0;
    public double f = 0;

    public Node(int x, int y, int g, int f, int gridWidth)
    {
        this.x = x;
        this.y = y;
        this.g = g;
        this.f = f;
        this.gridWidth = gridWidth;
    }

    public Node(int x, int y)
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
        result = prime * result + x;
        result = prime * result + y;
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
