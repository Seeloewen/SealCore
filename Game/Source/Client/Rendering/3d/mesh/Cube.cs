namespace SealCore.Client.rendering;

public class Cube
{



    internal float[] getVertices()
    {
        return new[]
        {

            0f, 0f, 0f, .8f,
            0f, 0f, 1f, .8f,
            0f, 1f, 0f, .8f,
            0f, 0f, 1f, .8f,
            0f, 1f, 0f, .8f,
            0f, 1f, 1f, .8f,

            1f, 0f, 0f, .2f,
            1f, 0f, 1f, .2f,
            1f, 1f, 0f, .2f,
            1f, 0f, 1f, .2f,
            1f, 1f, 0f, .2f,
            1f, 1f, 1f, .2f,

            0f, 0f, 0f, .7f,
            0f, 0f, 1f, .7f,
            1f, 0f, 0f, .7f,
            0f, 0f, 1f, .7f,
            1f, 0f, 0f, .7f,
            1f, 0f, 1f, .7f,

            0f, 1f, 0f, .3f,
            0f, 1f, 1f, .3f,
            1f, 1f, 0f, .3f,
            0f, 1f, 1f, .3f,
            1f, 1f, 0f, .3f,
            1f, 1f, 1f, .3f,

            0f, 0f,0f, .6f, 
            0f, 1f,0f, .6f, 
            1f, 0f,0f, .6f, 
            0f, 1f,0f, .6f, 
            1f, 0f,0f, .6f, 
            1f, 1f,0f, .6f, 

            0f, 0f, 1f, .4f,
            0f, 1f, 1f, .4f,
            1f, 0f, 1f, .4f,
            0f, 1f, 1f, .4f,
            1f, 0f, 1f, .4f,
            1f, 1f, 1f, .4f,
            
        };
    }
    
    
    
    
}