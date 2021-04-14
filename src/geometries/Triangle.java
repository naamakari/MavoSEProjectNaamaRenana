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

//    /**
//     * getter for vertex 1
//     * @return vertex 1
//     */
//    public Point3D getVertex1() {
//        return _vertex1;
//    }
//
//    /**
//     * getter for vertex 2
//     * @return vertex 2
//     */
//    public Point3D getVertex2() {
//        return _vertex2;
//    }
//
//    /**
//     * getter for vertex 3
//     * @return vertex 3
//     */
//    public Point3D getVertex3() {
//        return _vertex3;
//    }

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

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        //the point that we found at the plane
        List<Point3D> list = plane.findIntersections(ray);
        if(list==null) {
            return null;
        }

        Point3D p = list.get(0);

        //we will check if the point is inside the triangle
        Vector v1 = vertices.get(0).subtract(ray.getP0());
        Vector v2 = vertices.get(1).subtract(ray.getP0());
        Vector v3 = vertices.get(2).subtract(ray.getP0());

        Vector n1 = v2.crossProduct(v1).normalize();
        Vector n2 = v3.crossProduct(v2).normalize();
        Vector n3 = v1.crossProduct(v3).normalize();

        double res1 = ray.getDir().dotProduct(n1);
        double res2 = ray.getDir().dotProduct(n2);
        double res3 = ray.getDir().dotProduct(n3);

//check the sign of the projection
        if ((res1 > 0 && res2 > 0 && res3 > 0) || (res1 < 0 && res2 < 0 && res3 < 0)) {
            return list;
        }
        return null;
    }
}
