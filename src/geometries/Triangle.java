package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * class that implements the geometry interface and extends from polygon
 * contain 3 points for triangle
 */
public class Triangle extends Polygon {

    //constructor
    public Triangle(Point3D vertex1, Point3D vertex2, Point3D vertex3) {
        super(vertex1, vertex2, vertex3);
    }

    @Override
    public Vector getNormal(Point3D point) {
        return plane.getNormal(point);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                '}';
    }

}
