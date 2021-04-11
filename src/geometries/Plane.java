package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * class that implements geometry interface
 * have point and normal vector
 */
public class Plane implements Geometry {
    final Point3D _q0;
    final Vector _normal;

    //constructor of 3 points
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0 = p1;
        //check direction of vectors
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);

//checks if the points are the same
        if (p1.equals(p2) || p2.equals(p3) || p1.equals(p3)) {
            throw new IllegalArgumentException("points cannot be the same");
        }

        if (p1.isSameLine(p2) && p2.isSameLine(p3)) {
            throw new IllegalArgumentException("points are on the same line");
        }
        Vector n = v1.crossProduct(v2);

        _normal =n.normalize();
    }


    /**
     * constructor of point and vector
     * normal vector for plane, will normalized automatically
     *
     * @param point
     * @param vector
     */
    public Plane(Point3D point, Vector vector) {
        _q0 = point;
        _normal = vector.normalized();
    }

    /**
     * getter for field q0
     *
     * @return q0
     */
    public Point3D getQ0() {
        return _q0;
    }

    /**
     * getter of the normal vector of the Plane
     *
     * @return reference to normal vector of the plane
     * @deprecated use the overridden version from Geometry
     * {@link Plane#getNormal(Point3D)} with null for parameter value.
     */
    @Deprecated
    public Vector getNormal() {
        return _normal;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return _normal;
    }

}
