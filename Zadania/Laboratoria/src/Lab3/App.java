package Lab3;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App
{
    static int width;
    static int height;

    public static void main(String[] args)
    {
//        Renderer renderer1 = new Renderer("src/Lab3/img/test_trojkat1.png", 100, 100);
//        rysujTrojkat(renderer1, new Vec2f(30, 30), new Vec2f(70, 50), new Vec2f(50, 70), Color.BLUE);
//        Renderer renderer2 = new Renderer("src/Lab3/img/test_trojkat2.png", 200, 200);
//        rysujTrojkat(renderer2, new Vec2f(60, 60), new Vec2f(140, 100), new Vec2f(100, 140), Color.MAGENTA);
//        Renderer renderer3 = new Renderer("src/Lab3/img/test_trojkat3.png", 500, 500);
//        rysujTrojkat(renderer3, new Vec2f(50, 50), new Vec2f(450, 50), new Vec2f(150, 450), Color.ORANGE);
//        Renderer renderer4 = new Renderer("src/Lab3/img/test_trojkat4.png", 500, 500);
//        rysujTrojkat(renderer4, new Vec2f(90, 410), new Vec2f(250, 375), new Vec2f(375, 75), Color.RED);
//        Renderer renderer5 = new Renderer("src/Lab3/img/test_trojkat5.png", 500, 500);
//        rysujTrojkat(renderer5, new Vec2f(100, 150), new Vec2f(250, 400), new Vec2f(400, 250), Color.GREEN);

        String sciezka = inputString("\nPodaj lokalizację pliku, format 'src/sciezka/do/pliku/obraz.png': ");
        if (!sciezka.startsWith("src")) sciezka = "src/" + sciezka;
        if (!sciezka.endsWith(".png")) sciezka = sciezka + ".png";

        Renderer renderer = stworzRenderer(sciezka);

        String menu = "\n=======   WYBIERZ KOLOR   =======" +
                "\n1. Biały" + "\n2. Żółty" + "\n3. Pomarańczowy" + "\n4. Różowy" + "\n5. Purpurowy" +
                "\n6. Czerwony" + "\n7. Zielony" + "\n8. Morski" + "\n9. Niebieski" + "\n10. Szary" +
                "\n11. Czarny" + "\nWybierz opcję z zakresu od 1 do 11: ";
        int kolorInt = inputInt(menu, true, 1, 11);
        Color color = getColor(kolorInt);

        int x1 = inputInt("\nPodaj współrzędną x dla pierwszego punktu z zakresu od 0 do " + (width - 1) + ": ", true, 0, width - 1);
        int y1 = inputInt("\nPodaj współrzędną y dla pierwszego punktu z zakresu od 0 do " + (height - 1) + ": ", true, 0, height - 1);
        int x2 = inputInt("\nPodaj współrzędną x dla drugiego punktu z zakresu od 0 do " + (width - 1) + ": ", true, 0, width - 1);
        int y2 = inputInt("\nPodaj współrzędną y dla drugiego punktu z zakresu od 0 do " + (height - 1) + ": ", true, 0, height - 1);
        int x3 = inputInt("\nPodaj współrzędną x dla trzeciego punktu z zakresu od 0 do " + (width - 1) + ": ", true, 0, width - 1);
        int y3 = inputInt("\nPodaj współrzędną y dla trzeciego punktu z zakresu od 0 do " + (height - 1) + ": ", true, 0, height - 1);

        rysujTrojkat(renderer, new Vec2f(x1, y1), new Vec2f(x2, y2), new Vec2f(x3, y3), color);
    }

    private static Color getColor(int kolorInt)
    {
        Color color;
        if (kolorInt == 1) color = Color.WHITE;
        else if (kolorInt == 2) color = Color.YELLOW;
        else if (kolorInt == 3) color = Color.ORANGE;
        else if (kolorInt == 4) color = Color.PINK;
        else if (kolorInt == 5) color = Color.MAGENTA;
        else if (kolorInt == 6) color = Color.RED;
        else if (kolorInt == 7) color = Color.GREEN;
        else if (kolorInt == 8) color = Color.CYAN;
        else if (kolorInt == 9) color = Color.BLUE;
        else if (kolorInt == 10) color = Color.GRAY;
        else color = Color.BLACK;
        return color;
    }

    public static Renderer stworzRenderer(String sciezka)
    {
        width = inputInt("\nPodaj szerokość obrazu: ", false, 0, 0);
        height = inputInt("\nPodaj wysokość obrazu: ", false, 0, 0);
        return new Renderer(sciezka, width, height);
    }

    public static void rysujTrojkat(Renderer renderer, Vec2f P1, Vec2f P2, Vec2f P3, Color color)
    {
        renderer.clear();
        renderer.drawTriangle(P1, P2, P3, color);
        saveRenderer(renderer);
    }

    public static String inputString(String tekst)
    {
        System.out.print(tekst);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static int inputInt(String tekst, boolean mniejsze, int start, int length)
    {
        Scanner scanner = new Scanner(System.in);
        int number;
        while (true)
        {
            try
            {
                System.out.print(tekst);
                number = Integer.parseInt(scanner.nextLine());
                if (!mniejsze && number <= 0)
                {
                    System.out.println("Musisz podać wartość większą od 0!");
                    continue;
                }
                if (mniejsze && (number > length || number < start))
                {
                    System.out.println("Musisz podać wartość z zakresu od " + start + " do " + length + "!");
                    continue;
                }
                break;
            }
            catch (Exception ex)
            {
                System.out.println("Musisz podać wartość typu 'int'!");
            }
        }
        return number;
    }

    public static void saveRenderer(Renderer renderer)
    {
        try
        {
            renderer.save();
        }
        catch (IOException ex)
        {
            Logger.getLogger(Lab2.App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
