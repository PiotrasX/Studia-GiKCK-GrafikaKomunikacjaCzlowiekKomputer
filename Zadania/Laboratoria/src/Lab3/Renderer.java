package Lab3;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Renderer
{
    private BufferedImage render;
    private final String fileName;

    public Renderer(String fileName, int width, int height)
    {
        render = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.fileName = fileName;
    }

    public Vec3f barycentric(Vec2f A, Vec2f B, Vec2f C, Vec2f P)
    {
        Vec3f v1 = new Vec3f(B.x - A.x, C.x - A.x, A.x - P.x);
        Vec3f v2 = new Vec3f(B.y - A.y, C.y - A.y, A.y - P.y);
        Vec3f cross = Vec3f.cross(v1, v2);

        if (Math.abs(cross.z) < 1) return new Vec3f(-1, -1, -1);
        float u = cross.x / cross.z;
        float v = cross.y / cross.z;
        return new Vec3f(u, v, 1.f - u - v);
    }

    public void drawTriangle(Vec2f A, Vec2f B, Vec2f C, Color color)
    {
        int minX = (int) Math.min(A.x, Math.min(B.x, C.x));
        int maxX = (int) Math.max(A.x, Math.max(B.x, C.x));
        int minY = (int) Math.min(A.y, Math.min(B.y, C.y));
        int maxY = (int) Math.max(A.y, Math.max(B.y, C.y));

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
        render = Lab2.Renderer.verticalFlip(render);
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
