package Lab4;

import java.awt.*;

public class ZBufforRenderer extends Renderer
{
    private Vec3f lightDir;
    private final Vec3f cameraPosition = new Vec3f(0, 0, 3); // Pozycja kamery
    private float[][] zBuffor; // Z-buffor

    public ZBufforRenderer(String fileName) { super(fileName); inicjalizujSwiatlo(); inicjalizujZBuffor(); }
    public ZBufforRenderer(String filename, int width, int height) { super(filename, width, height); inicjalizujSwiatlo(); inicjalizujZBuffor(); }
    public ZBufforRenderer(String filename, float swiatloX, float swiatloY, float swiatloZ) { super(filename); inicjalizujSwiatlo(swiatloX, swiatloY, swiatloZ); inicjalizujZBuffor(); }
    public ZBufforRenderer(String filename, int width, int height, float swiatloX, float swiatloY, float swiatloZ) { super(filename, width, height); inicjalizujSwiatlo(swiatloX, swiatloY, swiatloZ); inicjalizujZBuffor(); }

    private void inicjalizujSwiatlo()
    {
        // Ustawienie kierunku światła (na razie ustawiony na stałe)
        lightDir = new Vec3f(0, 0, 1); // Światło padające z dołu do góry (prostopadle)
    }
    private void inicjalizujSwiatlo(float x, float y, float z)
    {
        // Ustawienie kierunku światła (na razie ustawiony na stałe)
        lightDir = new Vec3f(x, y, z);
    }
    private void inicjalizujZBuffor()
    {
        zBuffor = new float[render.getWidth()][render.getHeight()];
        for (int i = 0; i < render.getWidth(); i++)
        {
            for (int j = 0; j < render.getHeight(); j++)
            {
                zBuffor[i][j] = Float.POSITIVE_INFINITY; // Inicjalizacja z-buffora wartością 'nieskończoność'
            }
        }
    }

    public void render(Model model)
    {
        for (Vec3i face : model.getFaceList())
        {
            Vec2i[] screen_coords = new Vec2i[3];
            Vec3f[] world_coords = new Vec3f[3];

            world_coords[0] = model.getVertex(face.x);
            world_coords[1] = model.getVertex(face.y);
            world_coords[2] = model.getVertex(face.z);

            for (int j = 0; j < 3; j++)
            {
                screen_coords[j] = new Vec2i((int)((world_coords[j].x + 1.0) * render.getWidth() / 2.0),
                        (int)((world_coords[j].y + 1.0) * render.getHeight() / 2.0) - render.getHeight() / 2);
            }

            // Obliczanie wektora normalnego dla tej ściany
            Vec3f normal = Vec3f.cross(
                    Vec3f.subtract(world_coords[1], world_coords[0]),
                    Vec3f.subtract(world_coords[2], world_coords[0])
            ).normalize();

            // Obliczanie wektora od kamery do środka trójkąta
            Vec3f triangleCenter = new Vec3f(
                    (world_coords[0].x + world_coords[1].x + world_coords[2].x) / 3,
                    (world_coords[0].y + world_coords[1].y + world_coords[2].y) / 3,
                    (world_coords[0].z + world_coords[1].z + world_coords[2].z) / 3
            );
            Vec3f cameraToTriangle = Vec3f.subtract(triangleCenter, cameraPosition);

            // Sprawdzanie backface-culling za pomocą iloczynu skalarnego
            if (Vec3f.dot(normal, cameraToTriangle) < 0)
            {
                // Ściana jest zwrócona przodem do kamery, więc można ją narysować
                float intensity = Vec3f.dot(normal, lightDir.normalize());
                int colorValue = (int) (255 * Math.max(intensity, 0));
                Color  color = new Color(colorValue, colorValue, colorValue); // Odcienie szarości

                // Rysowanie trójkąta z użyciem z-buffora
                int minX = Math.max(0, Math.min(screen_coords[0].x, Math.min(screen_coords[1].x, screen_coords[2].x)));
                int maxX = Math.min(render.getWidth() - 1, Math.max(screen_coords[0].x, Math.max(screen_coords[1].x, screen_coords[2].x)));
                int minY = Math.max(0, Math.min(screen_coords[0].y, Math.min(screen_coords[1].y, screen_coords[2].y)));
                int maxY = Math.min(render.getHeight() - 1, Math.max(screen_coords[0].y, Math.max(screen_coords[1].y, screen_coords[2].y)));
                sprawdzBarycentric(minX, maxX, minY, maxY, world_coords, color, screen_coords[0], screen_coords[1], screen_coords[2]);
            }
        }
    }

    public void sprawdzBarycentric(int minX, int maxX, int minY, int maxY, Vec3f[] world_coords, Color color, Vec2i A, Vec2i B, Vec2i C)
    {
        for (int x = minX; x <= maxX; x++)
        {
            for (int y = minY; y <= maxY; y++)
            {
                Vec2f P = new Vec2f(x, y);
                Vec3f barycentric = barycentric(A, B, C, P);

                if (barycentric.x >= 0 && barycentric.y >= 0 && barycentric.z >= 0)
                {
                    // Obliczanie głębokości 'z' dla piksela P
                    float z = world_coords[0].z * barycentric.x +
                            world_coords[1].z * barycentric.y +
                            world_coords[2].z * barycentric.z;

                    // Sprawdzenie i aktualizacja z-buffora
                    if (zBuffor[x][y] > z)
                    {
                        zBuffor[x][y] = z;
                        drawPoint(x, y, color); // Rysowanie piksela, jeśli przeszedł test z-buffora
                    }
                }
            }
        }
    }
}
