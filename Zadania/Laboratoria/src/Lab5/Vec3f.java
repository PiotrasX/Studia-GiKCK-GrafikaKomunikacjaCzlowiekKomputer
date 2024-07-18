package Lab5;

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

    public static Vec3f add(Vec3f v1, Vec3f v2) { return new Vec3f(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z); }
    public static Vec3f subtract(Vec3f v1, Vec3f v2) { return new Vec3f(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z); }
    public Vec3f add(Vec3f vec) { return new Vec3f(this.x + vec.x, this.y + vec.y, this.z + vec.z); }
    public Vec3f subtract(Vec3f vec) { return new Vec3f(this.x - vec.x, this.y - vec.y, this.z - vec.z); }
    public static float dot(Vec3f v1, Vec3f v2) { return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z; }
    public Vec3f normalize()
    {
        float length = (float) Math.sqrt(x * x + y * y + z * z);
        if (length > 0) return new Vec3f(x / length, y / length, z / length);
        return new Vec3f(0, 0, 0); // Zwracanie wektora zerowego w przypadku, gdy długość jest równa 0
    }
}
