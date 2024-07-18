package Lab5;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Renderer
{
    protected BufferedImage render;
    private final String fileName;

    public Renderer(String fileName)
    {
        render = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
        this.fileName = fileName;
    }
    public Renderer(String fileName, int width, int height)
    {
        render = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.fileName = fileName;
    }

    public Vec3f barycentric(Vec2i A, Vec2i B, Vec2i C, Vec2f P)
    {
        Vec3f v1 = new Vec3f(B.x - A.x, C.x - A.x, A.x - P.x);
        Vec3f v2 = new Vec3f(B.y - A.y, C.y - A.y, A.y - P.y);
        Vec3f cross = Vec3f.cross(v1, v2);

        if (Math.abs(cross.z) < 1) return new Vec3f(-1, -1, -1);
        float u = cross.x / cross.z;
        float v = cross.y / cross.z;
        return new Vec3f(u, v, 1.f - u - v);
    }

    public void drawTriangle(Vec2i A, Vec2i B, Vec2i C, Vec3i color)
    {
        int red = Math.min(Math.max(color.x, 0), 255);
        int green = Math.min(Math.max(color.y, 0), 255);
        int blue = Math.min(Math.max(color.z, 0), 255);

        int minX = Math.min(A.x, Math.min(B.x, C.x));
        int maxX = Math.max(A.x, Math.max(B.x, C.x));
        int minY = Math.min(A.y, Math.min(B.y, C.y));
        int maxY = Math.max(A.y, Math.max(B.y, C.y));
        sprawdzBarycentric(minX, maxX, minY, maxY, new Color(red, green, blue), A, B, C);
    }
    public void drawTriangle(Vec2i A, Vec2i B, Vec2i C, Color color)
    {
        int minX = Math.min(A.x, Math.min(B.x, C.x));
        int maxX = Math.max(A.x, Math.max(B.x, C.x));
        int minY = Math.min(A.y, Math.min(B.y, C.y));
        int maxY = Math.max(A.y, Math.max(B.y, C.y));
        sprawdzBarycentric(minX, maxX, minY, maxY, color, A, B, C);
    }

    public void sprawdzBarycentric(int minX, int maxX, int minY, int maxY, Color color, Vec2i A, Vec2i B, Vec2i C)
    {
        for (int x = minX; x <= maxX; x++)
        {
            for (int y = minY; y <= maxY; y++)
            {
                Vec2f P = new Vec2f(x, y);
                Vec3f barycentric = barycentric(A, B, C, P);

                if (barycentric.x >= 0 && barycentric.y >= 0 && barycentric.z >= 0) drawPoint(x, y, color);
            }
        }
    }

    public void drawPoint(int x, int y, Color color) { render.setRGB(x, y, color.getRGB()); }

    public void save() throws IOException
    {
        File outputFile = new File(fileName);
        render = Renderer.verticalFlip(render);
        ImageIO.write(render, "png", outputFile);
    }

    public void clear()
    {
        for (int x = 0; x < render.getWidth(); x++)
        {
            for (int y = 0; y < render.getHeight(); y++)
            {
                Color white = new Color(255, 255, 255);
                render.setRGB(x, y, white.getRGB());
            }
        }
    }

    public void koloruj(Color color)
    {
        for (int x = 0; x < render.getWidth(); x++)
        {
            for (int y = 0; y < render.getHeight(); y++)
            {
                render.setRGB(x, y, color.getRGB());
            }
        }
    }

    public static BufferedImage verticalFlip(BufferedImage img)
    {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage flippedImage = new BufferedImage(w, h, img.getColorModel().getTransparency());
        Graphics2D g = flippedImage.createGraphics();
        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
        g.dispose();
        return flippedImage;
    }
}
