using OpenTK.Graphics.OpenGL4;

namespace SealCore.Client.rendering;



internal record VBElement(int count)
{
}

internal record VBLayout(VBElement[] elements/*, bool instanced, bool dynamic*/)
{
}


internal class VertexArray
{

    private int handle;

    private int[] vbos;
    
    public VertexArray(VBLayout[] layouts)
    {
        handle = GL.GenVertexArray();
        
        vbos = new int[layouts.Length];

        int iAttribute = 0;
        
        for (int iBuffer = 0; iBuffer < layouts.Length; iBuffer++)
        {
            int vbo = GL.GenBuffer();
            vbos[iBuffer] = vbo;
            Bind();
            GL.BindBuffer(BufferTarget.ArrayBuffer, vbo);
            
            var layout = layouts[iBuffer];
            var elements =  layout.elements;

            int offset = 0;
            int stride = calcStride(elements);
            for (int iElement = 0; iElement < elements.Length; iElement++)
            {
                var element = elements[iElement];
                int count = element.count;
                GL.EnableVertexAttribArray(iAttribute);
                GL.VertexAttribPointer(iAttribute, count , VertexAttribPointerType.Float, false, stride, offset);
                offset += count * sizeof(float);
                iAttribute++;
            }
            
            

        }
        
    }


    public void FillData(int vboIndex, float[] data)
    {
        GL.BindBuffer(BufferTarget.ArrayBuffer, vbos[vboIndex]);
        GL.BufferData(BufferTarget.ArrayBuffer, data.Length * sizeof(float), data, BufferUsageHint.StaticDraw);
    }
    
    

    public void Bind()
    {
        GL.BindVertexArray(handle);
    }


    private static int calcStride(VBElement[] elements)
    {
        int sum = 0;
        foreach(var element in elements)
        {
            sum += element.count * 4;
        }
        return sum;
    }
    
    
}