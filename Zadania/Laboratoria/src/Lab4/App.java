package Lab4;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App
{
    static int width;
    static int height;

    public static void main(String[] args) throws IOException
    {
        Model model = new Model();
        model.readOBJ("src/Lab4/img/deer.obj");

//        RandomColorRenderer renderer1 = new RandomColorRenderer("src/Lab4/img/test_random_color.png");
//        renderer1.render(model);
//        saveRenderer(renderer1);
//        FlatShadingRenderer renderer2 = new FlatShadingRenderer("src/Lab4/img/test_flat_shading.png");
//        renderer2.render(model);
//        saveRenderer(renderer2);
//        BackfaceCullingRenderer renderer3 = new BackfaceCullingRenderer("src/Lab4/img/test_backface_culling.png");
//        renderer3.render(model);
//        saveRenderer(renderer3);
//        ZBufforRenderer renderer4 = new ZBufforRenderer("src/Lab4/img/test_z_buffor.png");
//        renderer4.render(model);
//        saveRenderer(renderer4);
//        ZBufforRenderer renderer5 = new ZBufforRenderer("src/Lab4/img/test_z_buffor_1.png", 0f, 1f, 0f);
//        renderer5.render(model);
//        saveRenderer(renderer5);
//        ZBufforRenderer renderer6 = new ZBufforRenderer("src/Lab4/img/test_z_buffor_2.png", 1f, 1f, -0.25f);
//        renderer6.render(model);
//        saveRenderer(renderer6);
//        ZBufforRenderer renderer7 = new ZBufforRenderer("src/Lab4/img/test_z_buffor_3.png", 0f, 0.25f, 0.25f);
//        renderer7.render(model);
//        saveRenderer(renderer7);

//        Model model1 = new Model();
//        Model model2 = new Model();
//        model1.readOBJ("src/Lab4/img/deer.obj");
//        model2.readOBJ("src/Lab4/img/deer.obj");
//        RandomColorRenderer renderer8 = new RandomColorRenderer("src/Lab4/img/test_translate_normal.png", 500, 500);
//        RandomColorRenderer renderer9 = new RandomColorRenderer("src/Lab4/img/test_translate_vector.png", 500, 500);
//        Vec3f translationVector = new Vec3f(0.1f, 0.3f, 0.25f);
//        model2.translate(translationVector);
//        renderer8.clear();
//        renderer9.clear();
//        renderer8.render(model1);
//        renderer9.render(model2);
//        saveRenderer(renderer8);
//        saveRenderer(renderer9);

        String sciezka = inputString("\nPodaj lokalizację pliku, format 'src/sciezka/do/pliku/obraz.png': ");
        if (!sciezka.startsWith("src")) sciezka = "src/" + sciezka;
        if (!sciezka.endsWith(".png")) sciezka = sciezka + ".png";

        String menu = "\n=======   WYBIERZ RENDER   =======" +
                "\n1. Random Colors" + "\n2. Flat Shading" + "\n3. Backface Culling" + "\n4. Z-Buffor" +
                "\nWybierz opcję z zakresu od 1 do 4: ";
        int render = inputInt(menu, true, 1, 4);
        podajWymiary();

        if (render == 1)
        {
            RandomColorRenderer renderer = new RandomColorRenderer(sciezka, width, height);
            renderer.render(model);
            saveRenderer(renderer);
        }
        else if (render == 2)
        {
            FlatShadingRenderer renderer = new FlatShadingRenderer(sciezka, width, height);
            renderer.render(model);
            saveRenderer(renderer);
        }
        else if (render == 3)
        {
            BackfaceCullingRenderer renderer = new BackfaceCullingRenderer(sciezka, width, height);
            renderer.render(model);
            saveRenderer(renderer);
        }
        else
        {
            ZBufforRenderer renderer = new ZBufforRenderer(sciezka, width, height);
            renderer.render(model);
            saveRenderer(renderer);
        }
    }

    public static void podajWymiary()
    {
        width = inputInt("\nPodaj szerokość obrazu: ", false, 0, 0);
        height = inputInt("\nPodaj wysokość obrazu: ", false, 0, 0);
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
                if (!mniejsze && number < 200)
                {
                    System.out.println("Musisz podać wartość równą bądź większą od 200!");
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
