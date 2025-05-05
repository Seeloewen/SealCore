import java.time.LocalTime;

public class Main
{
    public static FastNoiseLite perlin = new FastNoiseLite(LocalTime.now().toSecondOfDay());
    public static float frequency = 0.1f;

    public static void main(String[] args)
    {
        System.out.println("3D Perlin Noise Example using frequency " + frequency);

        //Setup noise generator
        perlin.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
        perlin.SetFrequency(frequency);

        //Generate pattern
        for (int i = 0; i < 50; i++)
        {
            for (int j = 0; j < 50; j++)
            {
                //System.out.print(perlin.GetNoise(i, j));
                //System.out.print(parseNoiseToHeight((perlin.GetNoise(i, j) + 1.0f) / 2.0f)); //Shift value by 0.5f to get range from 0 to 1
                System.out.print(parseNoise((perlin.GetNoise(i, j) + 1.0f) / 2.0f)); //Shift value by 0.5f to get range from 0 to 1
                System.out.print("|");
            }
            System.out.print("\n");
        }
    }

    public static String parseNoise(float noise)
    {
        //Evaluate noise value and return appropriate "Terrain Height"
        if (noise >= 0.65)
        {
            return "M"; //Mountain
        } else if (noise < 0.65 && noise > 0.35)
        {
            return "-"; //Flat
        } else if (noise <= 0.35)
        {
            return "V"; //Flat
        }

        return "E"; //Error
    }

    public static int parseNoiseToHeight(float noise)
    {
        return (int)(noise * 75); //Returns a height between 0 and 75
    }
}