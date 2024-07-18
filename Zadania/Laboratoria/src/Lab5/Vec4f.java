package Lab5;

public class Vec4f
{
    public float x;
    public float y;
    public float z;
    public float w;

    public Vec4f(float x, float y, float z, float w)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @Override
    public String toString() { return x + " " + y + " " + z + " " + w; }

    public static Vec4f add(Vec4f v1, Vec4f v2) { return new Vec4f(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z, v1.w + v2.w); }
    public static Vec4f subtract(Vec4f v1, Vec4f v2) { return new Vec4f(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z, v1.w - v2.w); }
    public Vec4f add(Vec4f vec) { return new Vec4f(this.x + vec.x, this.y + vec.y, this.z + vec.z, this.w + vec.w); }
    public Vec4f subtract(Vec4f vec) { return new Vec4f(this.x - vec.x, this.y - vec.y, this.z - vec.z, this.w - vec.w); }

    public Vec4f normalize()
    {
        float length = (float) Math.sqrt(x * x + y * y + z * z + w * w);
        if (length > 0) return new Vec4f(x / length, y / length, z / length, w / length);
        return new Vec4f(0, 0, 0, 0);
    }
}
