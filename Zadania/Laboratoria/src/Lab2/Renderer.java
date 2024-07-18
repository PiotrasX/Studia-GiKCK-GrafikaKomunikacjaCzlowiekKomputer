package Lab2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Renderer
{
    public enum LineAlgo {NAIVE, DDA, BRESENHAM, BRESENHAM_INT}

    private BufferedImage render;
    // public final int defaultHeight = 200;
    // public final int defaultWidth = 200;

    private final String fileName;
    // private final LineAlgo lineAlgo = LineAlgo.NAIVE;
    // private final LineAlgo lineAlgo = LineAlgo.DDA;
    // private final LineAlgo lineAlgo = LineAlgo.BRESENHAM;
    // private final LineAlgo lineAlgo = LineAlgo.BRESENHAM_INT;
    public LineAlgo lineAlgo;

    public Renderer(String fileName, int width, int height)
    {
        render = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.fileName = fileName;
    }

    public Renderer(String fileName, int width, int height, LineAlgo lineAlgo)
    {
        render = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.fileName = fileName;
        this.lineAlgo = lineAlgo;
    }

    public void drawPoint(int x, int y)
    {
        int black = 0;
        render.setRGB(x, y, black);
    }

    public void drawLine(int x0, int y0, int x1, int y1, LineAlgo lineAlgo)
    {
        if (lineAlgo == LineAlgo.NAIVE) drawLineNaive(x0, y0, x1, y1);
        if (lineAlgo == LineAlgo.DDA) drawLineDDA(x0, y0, x1, y1);
        if (lineAlgo == LineAlgo.BRESENHAM) drawLineBresenham(x0, y0, x1, y1);
        if (lineAlgo == LineAlgo.BRESENHAM_INT) drawLineBresenhamInt(x0, y0, x1, y1);
    }

    public void drawLineNaive(int x0, int y0, int x1, int y1)
    {
        // TODO: zaimplementuj
        if (x0 == x1)
        {
            int startY = Math.min(y0, y1);
            int koniecY = Math.max(y0, y1);
            for (int y = startY; y <= koniecY; y++)
            {
                drawPoint(x0, y);
            }
        }

        if (x1 < x0)
        {
            int trzymaj = x0;
            x0 = x1;
            x1 = trzymaj;

            trzymaj = y0;
            y0 = y1;
            y1 = trzymaj;
        }

        int dx = x1 - x0;
        int dy = y1 - y0;

        float a = (float) dy / dx;
        for (float x = x0; x <= x1; x += 0.01F)
        {
            int y = y0 + Math.round(a * (x - x0));
            drawPoint(Math.round(x), y);
        }
    }

    public void drawLineDDA(int x0, int y0, int x1, int y1)
    {
        // TODO: zaimplementuj
        int dx = x1 - x0;
        int dy = y1 - y0;
        int kroki = Math.max(Math.abs(dx), Math.abs(dy));

        float xInkrementacja = (float) dx / kroki;
        float yInkrementacja = (float) dy / kroki;

        float x = x0;
        float y = y0;

        for (int i = 0; i <= kroki; i++)
        {
            drawPoint(Math.round(x), Math.round(y));
            x += xInkrementacja;
            y += yInkrementacja;
        }
    }

    public void drawLineBresenham(int x0, int y0, int x1, int y1)
    {
        // TODO: zaimplementuj
        boolean krok = Math.abs(y1 - y0) > Math.abs(x1 - x0);

        if (krok)
        {
            int trzymaj = x0;
            x0 = y0;
            y0 = trzymaj;

            trzymaj = x1;
            x1 = y1;
            y1 = trzymaj;
        }

        if (x1 < x0)
        {
            int trzymaj = x0;
            x0 = x1;
            x1 = trzymaj;

            trzymaj = y0;
            y0 = y1;
            y1 = trzymaj;
        }

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        float roznica = Math.abs((float) dy / dx);
        float blad = 0;

        int y = y0;
        int ykrok = y0 < y1 ? 1 : -1;

        for (int x = x0; x <= x1; x++)
        {
            if (krok) drawPoint(y, x);
            else drawPoint(x, y);

            blad += roznica;
            if (blad > 0.5F)
            {
                y += ykrok;
                blad -= 1.F;
            }
        }
    }

    public void drawLineBresenhamInt(int x0, int y0, int x1, int y1)
    {
        // TODO: zaimplementuj
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int blad = dx - dy;
        int bladx2;

        while (true)
        {
            drawPoint(x0, y0);
            if (x0 == x1 && y0 == y1) break;
            bladx2 = blad * 2;

            if (bladx2 > -dy)
            {
                blad -= dy;
                x0 += sx;
            }

            if (bladx2 < dx)
            {
                blad += dx;
                y0 += sy;
            }
        }
    }

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
