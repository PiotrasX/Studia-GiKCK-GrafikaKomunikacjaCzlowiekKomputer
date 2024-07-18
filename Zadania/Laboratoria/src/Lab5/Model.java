package Lab5;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Model
{
    private ArrayList<Vec3f> vertexList;
    private ArrayList<Vec3i> faceList;
    public Model() { }

    public List<Vec3f> getVertexList() { return vertexList; }
    public List<Vec3i> getFaceList() { return faceList; }
    public Vec3f getVertex(int index) { return vertexList.get(index); }
    public Vec3i getFace(int index) { return faceList.get(index); }

    public void readOBJ(String path) throws IOException
    {
        vertexList = new ArrayList<>();
        faceList = new ArrayList<>();
        InputStream objInputStream = new FileInputStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(objInputStream));
        vertexList.add(new Vec3f(0, 0, 0));
        while (reader.ready())
        {
            String line = reader.readLine();
            if (isVertex(line)) vertexList.add(parseVertexFromOBJ(line));
            else if (isFace(line)) faceList.add(parseFaceFromOBJ(line));
        }
    }

    public void translate(Vec3f translationVector) { vertexList.replaceAll(vec3f -> vec3f.add(translationVector)); }

    private boolean isVertex(String line) { return line.charAt(0) == 'v' && line.charAt(1) == ' '; }
    private boolean isFace(String line) { return line.charAt(0) == 'f' && line.charAt(1) == ' '; }

    private Vec3f parseVertexFromOBJ(String line)
    {
        String[] splitted = line.split(" ");
        // Jeśli jest to {"v", x_cord, y_cord, z_cord}, to zwróc Vec3f(Float.parseFloat(splitted[1]), Float.parseFloat(splitted[2]), Float.parseFloat(splitted[3]));
        return new Vec3f(Float.parseFloat(splitted[1]), Float.parseFloat(splitted[2]), Float.parseFloat(splitted[3]));
    }

    private Vec3i parseFaceFromOBJ(String line)
    {
        String[] splitted = line.split(" ");
        // Jeśli linia zaczyna się od "f", to następnie zawiera trzy zestawy indeksów: indeks wierzchołka, indeks tekstury i indeks normalnej, rozdzielone ukośnikami "/"
        return new Vec3i(Integer.parseInt(splitted[1].split("/")[0]), Integer.parseInt(splitted[2].split("/")[0]),
                Integer.parseInt(splitted[3].split("/")[0]));
        // Aby uzyskać indeks wierzchołka, indeks tekstury lub indeks normalnej, należy podzielić ciąg znaków na podstawie ukośnika "/"
    }

    public void transformVertices(Matrix4f transform)
    {
        ArrayList<Vec3f> transformedList = new ArrayList<>();
        for (Vec3f vertex : vertexList)
        {
            Vec4f extendedVertex = new Vec4f(vertex.x, vertex.y, vertex.z, 1.0f);
            Vec4f transformedVertex = transform.multiply(extendedVertex);
            transformedList.add(new Vec3f(
                transformedVertex.x / transformedVertex.w,
                transformedVertex.y / transformedVertex.w,
                transformedVertex.z / transformedVertex.w
            ));
        }
        vertexList = transformedList;
    }

    public void printModelVertices() {
        int i = 0;
        for (Vec3f vertex : vertexList) {
            System.out.println(vertex);
            i++;
            if (i == 3) break;
        }
    }
}
