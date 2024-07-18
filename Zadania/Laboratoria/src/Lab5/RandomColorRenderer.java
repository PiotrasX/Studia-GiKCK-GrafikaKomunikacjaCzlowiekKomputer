package Lab5;

import java.util.concurrent.ThreadLocalRandom;

public class RandomColorRenderer extends Renderer
{
    public RandomColorRenderer(String fileName) { super(fileName); }
    public RandomColorRenderer(String filename, int width, int height) { super(filename, width, height); }

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

            Vec3i randColor = new Vec3i(
                    ThreadLocalRandom.current().nextInt(256), // Red
                    ThreadLocalRandom.current().nextInt(256), // Green
                    ThreadLocalRandom.current().nextInt(256)  // Blue
            );
            drawTriangle(screen_coords[0], screen_coords[1], screen_coords[2], randColor);
        }
    }
}
