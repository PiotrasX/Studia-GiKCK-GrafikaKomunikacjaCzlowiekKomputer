package Lab1;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class App
{
    public static void main(String[] args)
    {
        BufferedImage img = null;
        File file = null;

        // Wczytywanie obrazu
        try
        {
            file = new File("src/Lab1/img/all_black.png");
            img = ImageIO.read(file);
        }
        catch (IOException e)
        {
            System.out.println("Błąd wczytywania obrazu: " + e);
        }

        // Pobranie szerokości i wysokości obrazu
        assert img != null;
        int width = img.getWidth();
        int height = img.getHeight();
        // Pobranie środkowego piksela
        int p = img.getRGB(width / 2, height / 2);

        // Odczytanie wartości kanałów przesuwając o odpowiednią liczbę bitów w prawo, tak aby kanał znalazł się
        // na bitach 7-0, następnie zerujemy pozostałe bity używając bitowego AND z maską 0xFF
        int a = (p >> 24) & 0xff;
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;
        System.out.println("Wartość 'alpha': " + a);
        System.out.println("Wartość 'red': " + r);
        System.out.println("Wartość 'green': " + g);
        System.out.println("Wartość 'blue': " + b);

        // Ustawiamy wartości poszczególnych kanalów na przykładowe liczby
        a = 255;
        r = 255;
        g = 255;
        b = 255;

        // Ustawiamy ponownie wartości kanałów dla zmiennej p
        p = (a << 24) | (r << 16) | (g << 8) | b;

        int promien = 30;
        rysujKropki(img, width / 2, height / 2, promien, p);
        rysujKropki(img, width / 4, height / 4, promien, p);
        rysujKropki(img, width / 4, height / 4 + height / 2, promien, p);
        rysujKropki(img, width / 4 + width / 2, height / 4, promien, p);
        rysujKropki(img, width / 4 + width / 2, height / 4 + height / 2, promien, p);

        // Zapisanie obrazu
        try
        {
            file = new File("src/Lab1/img/modified_all_black.png");
            ImageIO.write(img, "png", file);
        }
        catch (IOException e)
        {
            System.out.println("Błąd zapisu obrazu: " + e);
        }

        // Cały obraz biały
        allWhite(img);

        // Negatywne kolory obrazu
        imgNegative(img);
    }

    public static void rysujKropki(BufferedImage img, int centreWidth, int centreHeight, int radius, int color)
    {
        for (int i = -radius; i < radius; i++)
        {
            for (int j = -radius; j < radius; j++)
            {
                if (i * i + j * j <= radius * radius) img.setRGB(centreWidth + i, centreHeight + j, color);
            }
        }
    }

    public static void allWhite(BufferedImage img)
    {
        int width = img.getWidth();
        int height = img.getHeight();

        // TODO: zaimplementuj
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                img.setRGB(i, j, (255 << 24) | (255 << 16) | (255 << 8) | 255);
            }
        }
        zapisDoPliku(img, "src/Lab1/img/all_white.png");

        // TODO: zaimplementuj, za pomocą klasy Color
        Color color = new Color(255, 255, 255, 255);
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                img.setRGB(i, j, color.getRGB());
            }
        }
        zapisDoPliku(img, "src/Lab1/img/all_white_color_class.png");
    }

    public static void imgNegative(BufferedImage img)
    {
        // TODO: zaimplementuj
        img = odczytPliku();
        int width = img.getWidth();
        int height = img.getHeight();

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                int p = img.getRGB(i, j);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                r = 255 - r;
                g = 255 - g;
                b = 255 - b;

                p = (a << 24) | (r << 16) | (g << 8) | b;
                img.setRGB(i, j, p);
            }
        }
        zapisDoPliku(img, "src/Lab1/img/obraz_drzewo_negative.png");

        // TODO: zaimplementuj, za pomocą klasy Color
        img = odczytPliku();
        width = img.getWidth();
        height = img.getHeight();

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                Color color = new Color(img.getRGB(i, j), true);

                int r = 255 - color.getRed();
                int g = 255 - color.getGreen();
                int b = 255 - color.getBlue();
                int a = color.getAlpha();

                color = new Color(r, g, b, a);
                img.setRGB(i, j, color.getRGB());
            }
        }
        zapisDoPliku(img, "src/Lab1/img/obraz_drzewo_negative_color_class.png");
    }

    public static void zapisDoPliku(BufferedImage img, String src)
    {
        File file = null;
        try
        {
            file = new File(src);
            ImageIO.write(img, "png", file);
        }
        catch (IOException e)
        {
            System.out.println("Błąd zapisu pliku: " + e);
        }
    }

    public static BufferedImage odczytPliku()
    {
        File file;
        BufferedImage img = null;
        try
        {
            file = new File("src/Lab1/img/obraz_drzewo.jpg");
            img = ImageIO.read(file);
        }
        catch (IOException e)
        {
            System.out.println("Błąd odczytu pliku: " + e);
        }
        return img;
    }
}
