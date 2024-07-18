package Lab2;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App
{
    String version = "0.02";
    static int width;
    static int height;

    public static void main(String[] args)
    {
//        Renderer renderer2 = new Renderer("src/Lab2/img/line_naive.png", 100, 100);
//        renderer2.clear();
//        renderer2.drawLine(25, 70, 75, 30, Renderer.LineAlgo.NAIVE);
//        saveRenderer(renderer2);

        String sciezka = inputString("\nPodaj lokalizację pliku, format 'src/sciezka/do/pliku/obraz.png': ");
        if (!sciezka.startsWith("src")) sciezka = "src/" + sciezka;
        if (!sciezka.endsWith(".png")) sciezka = sciezka + ".png";

        String menu = "\n=========   MENU   =========" +
                "\n1. Narysuj punkt" +
                "\n2. Narysuj linię algorytmem naiwnym" +
                "\n3. Narysuj linię algorytmem DDA" +
                "\n4. Narysuj linię algorytmem Bresenhama" +
                "\n5. Narysuj linię algorytmem Bresenhama (Integer)" +
                "\nWybierz opcję z zakresu od 1 do 5: ";
        int algorytm = inputInt(menu, true, 1, 5);

        if (algorytm == 1)
        {
            Renderer mainRenderer = stworzRenderer(sciezka, null);
            int x = inputInt("\nPodaj wartość 'x' dla punktu z zakresu od 0 do " + (width - 1) + ": ", true, 0, width - 1);
            int y = inputInt("\nPodaj wartość 'y' dla punktu z zakresu od 0 do " + (height - 1) + ": ", true, 0, height - 1);

            // Początek obrazu to lewy dolny róg
            mainRenderer.clear();
            mainRenderer.drawPoint(x, y);
            saveRenderer(mainRenderer);
        }
        else if (algorytm == 2)
        {
            Renderer renderer = stworzRenderer(sciezka, Renderer.LineAlgo.NAIVE);
            podajPunktyRysujLinie(renderer, width, height);
        }
        else if (algorytm == 3)
        {
            Renderer renderer = stworzRenderer(sciezka, Renderer.LineAlgo.DDA);
            podajPunktyRysujLinie(renderer, width, height);
        }
        else if (algorytm == 4)
        {
            Renderer renderer = stworzRenderer(sciezka, Renderer.LineAlgo.BRESENHAM);
            podajPunktyRysujLinie(renderer, width, height);
        }
        else
        {
            Renderer renderer = stworzRenderer(sciezka, Renderer.LineAlgo.BRESENHAM_INT);
            podajPunktyRysujLinie(renderer, width, height);
        }
    }

    public static Renderer stworzRenderer(String sciezka, Renderer.LineAlgo lineAlgo)
    {
        width = inputInt("\nPodaj szerokość obrazu: ", false, 0, 0);
        height = inputInt("\nPodaj wysokość obrazu: ", false, 0, 0);
        if (lineAlgo == null) return new Renderer(sciezka, width, height);
        return new Renderer(sciezka, width, height, lineAlgo);
    }

    public static void podajPunktyRysujLinie(Renderer renderer, int w, int h)
    {
        int x0 = inputInt("\nPodaj wartość 'x' dla pierwszego punktu z zakresu od 0 do " + (w - 1) + ": ", true, 0, w - 1);
        int y0 = inputInt("\nPodaj wartość 'y' dla pierwszego punktu z zakresu od 0 do " + (h - 1) + ": ", true, 0, h - 1);
        int x1 = inputInt("\nPodaj wartość 'x' dla drugiego punktu z zakresu od 0 do " + (w - 1) + ": ", true, 0, w - 1);
        int y1 = inputInt("\nPodaj wartość 'y' dla drugiego punktu z zakresu od 0 do " + (h - 1) + ": ", true, 0, h - 1);
        renderer.clear();
        renderer.drawLine(x0, y0, x1, y1, renderer.lineAlgo);
        saveRenderer(renderer);
    }

    public static void saveRenderer(Renderer renderer)
    {
        try
        {
            renderer.save();
        }
        catch (IOException ex)
        {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public String getVersion() { return this.version; }
}
