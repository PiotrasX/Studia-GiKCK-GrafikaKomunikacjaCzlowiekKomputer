package Lab4;

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

    public static Vec3i subtract(Vec3i v1, Vec3i v2) { return new Vec3i(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z); }
    public Vec3i add(Vec3i vec) { return new Vec3i(this.x + vec.x, this.y + vec.y, this.z + vec.z); }
    public static float dot(Vec3i v1, Vec3i v2) { return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z; }
    public Vec3i normalize()
    {
        float length = (float) Math.sqrt(x * x + y * y + z * z);
        if (length > 0) return new Vec3i((int) (x / length), (int) (y / length), (int) (z / length));
        return new Vec3i(0, 0, 0); // Zwracanie wektora zerowego w przypadku, gdy długość jest równa 0
    }
}
