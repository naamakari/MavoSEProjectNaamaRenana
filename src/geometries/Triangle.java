package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * class that implements the geometry interface and extends from polygon
 * contain 3 points for triangle
 */
public class Triangle extends Polygon {
    Point3D _vertex1;
    Point3D _vertex2;
    Point3D _vertex3;

    //constructor
    public Triangle(Point3D vertex1, Point3D vertex2, Point3D vertex3) {
        super(vertex1,vertex2,vertex3);
    }

    /**
     * getter for vertex 1
     * @return vertex 1
     */
    public Point3D getVertex1() {
        return _vertex1;
    }

    /**
     * getter for vertex 2
     * @return vertex 2
     */
    public Point3D getVertex2() {
        return _vertex2;
    }

    /**
     * getter for vertex 3
     * @return vertex 3
     */
    public Point3D getVertex3() {
        return _vertex3;
    }

    @Override
    public Vector getNormal(Point3D point){
        return null;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertex1=" + _vertex1 +
                ", vertex2=" + _vertex2 +
                ", vertex3=" + _vertex3 +
                '}';
    }

}
