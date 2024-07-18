package Lab5;

import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

// 1. Stworzyć klasę Matrix4f.
// 2. Stworzyć klasę Vec4f na wzór klasy Vec3f, tylko że wektor posiada 4 współrzędne: x, y, z, w.
// 3. Dodać do klasy Model metodę translate(Vec3f vec), która przesuwa model (wszystkie jego wierzchołki) o dany wektor.
// 4. Wczytać plik deer.obj dwukrotnie, drugi z modeli przesunąć do tyłu i w prawo.
// 5. Uzyskać macierz M, którą użyjemy do przeliczenia współrzędnych globalnych do współrzędnych ekranu.

public class App
{
    public static void main(String[] args) throws IOException
    {
        Model model1 = new Model();
        Model model2 = new Model();
        model1.readOBJ("src/Lab5/img/deer.obj");
        model2.readOBJ("src/Lab5/img/deer.obj");

        // x -> + prawo/lewo -
        // y -> + góra/dół -
        // z -> + przód/tył -
        Vec3f translationVector = new Vec3f(0.1f, 0f, -10f); // Przesuwanie drugiego modelu
        model2.translate(translationVector);

        ZBufforRenderer renderer1 = new ZBufforRenderer("src/Lab5/img/test_translate_normal.png", 1000, 1000);
        ZBufforRenderer renderer2 = new ZBufforRenderer("src/Lab5/img/test_translate_vector.png", 1000, 1000);

        Color kolor = new Color(150, 100, 150);
        renderer1.koloruj(kolor);
        renderer2.koloruj(kolor);

        renderer1.render(model1);
        renderer2.render(model2);

        saveRenderer(renderer1);
        saveRenderer(renderer2);

        // Macierz perspektywiczna
        float fov = 60.0f; // Kąt widzenia w pionie
        float aspectRatio = 1920.0f / 1080.0f; // Proporcje ekranu
        float near = 0.1f; // Odległość do płaszczyzny bliskiej
        float far = 100.0f; // Odległość do płaszczyzny dalekiej
        Matrix4f perspectiveMatrix = Matrix4f.perspective(fov, aspectRatio, near, far);

        System.out.println("\nModel 1 - Współrzędne przed przekształceniem:");
        model1.printModelVertices();
        System.out.println("\nModel 2 - Współrzędne przed przekształceniem:");
        model2.printModelVertices();

        // Przeliczanie współrzędnych globalnych na współrzędne ekranu
        model1.transformVertices(perspectiveMatrix);
        model2.transformVertices(perspectiveMatrix);

        System.out.println("\nModel 1 - Współrzędne po przekształceniu:");
        model1.printModelVertices();
        System.out.println("\nModel 2 - Współrzędne po przekształceniu:");
        model2.printModelVertices();
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
