package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class that implements geometry interface
 * have point and normal vector
 */
public class Plane extends Geometry {
    final Point3D _q0;
    final Vector _normal;


    /**
     * constructor of 3 points
     *
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
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
        _normal = n.normalize();
        _box.setUpRightBehind(new Point3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        _box.setDownLeftFront(new Point3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        _box.setCenter(new Point3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));

    }


    /**
     * constructor of point and vector
     * normal vector for plane, will normalized automatically
     *
     * @param point  the point that build the plane
     * @param vector the vector that build the plane
     */
    public Plane(Point3D point, Vector vector) {
        _q0 = point;
        _normal = vector.normalized();//normalized
        _box.setUpRightBehind(new Point3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        _box.setDownLeftFront(new Point3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        _box.setCenter(new Point3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));

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
  public Box getBox() {
        return _box;
    }

    /**
     * getter of the normal vector of the Plane
     *
     * @param point the point we want to calculate the normal for
     * @return the normal
     */
    @Override
    public Vector getNormal(Point3D point) {
        return _normal;
    }

    /**
     * implements the method of find Geo intersections for plane
     *
     * @param ray         the specific ray
     * @param maxDistance the distance from the light
     * @return list of geo points intersection
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        Point3D P0 = ray.getP0();
        Vector v = ray.getDir();
        if (_q0.equals(P0)) {
            return null;
        }
        //create the vector from the point of the plane to start point of the ray
        Vector P0_Q0 = _q0.subtract(P0);

        double numerator = alignZero(_normal.dotProduct(P0_Q0));

//if the numerator equals to zero
        if (isZero(numerator)) {
            return null;
        }
        //the denominator
        double nv = alignZero(_normal.dotProduct(v));

        //ray is lying in the plane axis
        if (isZero(nv)) {
            return null;
        }

        double t = alignZero(numerator / nv);
        if (t <= 0 || alignZero(t - maxDistance) > 0) {
            return null;
        }

        Point3D P = ray.getPoint(t);
        return List.of(new GeoPoint(this, P));
    }
}
