package Lab4;

public class BackfaceCullingRenderer extends Renderer
{
    private Vec3f lightDir;
    private final Vec3f cameraPosition = new Vec3f(0, 0, 3); // Pozycja kamery

    public BackfaceCullingRenderer(String fileName) { super(fileName); inicjalizujSwiatlo(); }
    public BackfaceCullingRenderer(String filename, int width, int height) { super(filename, width, height); inicjalizujSwiatlo(); }

    private void inicjalizujSwiatlo()
    {
        // Ustawienie kierunku światła (na razie ustawiony na stałe)
        lightDir = new Vec3f(0, 0, 1); // Światło padające z dołu do góry (prostopadle)
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
                Vec3i color = new Vec3i(colorValue, colorValue, colorValue); // Odcienie szarości
                drawTriangle(screen_coords[0], screen_coords[1], screen_coords[2], color);
            }
        }
    }
}
