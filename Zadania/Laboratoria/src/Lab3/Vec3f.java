package Lab3;

public class Vec3f
{
    public float x;
    public float y;
    public float z;

    public Vec3f(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() { return x + " " + y + " " + z; }

    // Metoda do obliczania iloczynu wektorowego dwóch wektorów
    public static Vec3f cross(Vec3f v1, Vec3f v2)
    {
        return new Vec3f(
                v1.y * v2.z - v1.z * v2.y,
                v1.z * v2.x - v1.x * v2.z,
                v1.x * v2.y - v1.y * v2.x
        );
    }
}
