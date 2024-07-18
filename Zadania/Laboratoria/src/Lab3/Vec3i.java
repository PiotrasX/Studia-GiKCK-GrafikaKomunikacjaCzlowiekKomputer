package Lab3;

public class Vec3i
{
    public int x;
    public int y;
    public int z;

    public Vec3i(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() { return x + " " + y + " " + z; }

    // Metoda do obliczania iloczynu wektorowego dwóch wektorów
    public static Vec3i cross(Vec3i v1, Vec3i v2)
    {
        return new Vec3i(
                v1.y * v2.z - v1.z * v2.y,
                v1.z * v2.x - v1.x * v2.z,
                v1.x * v2.y - v1.y * v2.x
        );
    }
}
