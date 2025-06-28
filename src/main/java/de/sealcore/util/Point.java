package de.sealcore.util;

public class Point
{
    public double x;
    public double y;

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public void addX(double i)
    {
        x += i;
    }

    public void addY(double i)
    {
        y += i;
    }

    public boolean compareTo(Point p)
    {
        return p.x == x && p.y == y;
    }
}
