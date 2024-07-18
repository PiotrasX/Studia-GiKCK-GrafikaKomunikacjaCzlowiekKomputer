package Lab5;

public class FlatShadingRenderer extends Renderer
{
    private Vec3f lightDir;

    public FlatShadingRenderer(String fileName) { super(fileName); inicjalizujSwiatlo(); }
    public FlatShadingRenderer(String filename, int width, int height) { super(filename, width, height); inicjalizujSwiatlo(); }

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

            // Obliczanie intensywności światła
            float intensity = Vec3f.dot(normal, lightDir);

            // Zamiana intensywności na kolor (wartości od 0 do 1)
            int colorValue = (int) (255 * Math.max(intensity, 0));
            Vec3i color = new Vec3i(colorValue, colorValue, colorValue);
            drawTriangle(screen_coords[0], screen_coords[1], screen_coords[2], color);
        }
    }
}
