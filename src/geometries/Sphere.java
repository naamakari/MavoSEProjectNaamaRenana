package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * class that implements geometry interface
 * contain point of the center of the sphere and the radius
 */
public class Sphere extends Geometry {
    Point3D _center;
    double _radius;

    //constructor
    public Sphere(Point3D center, double radius) {
        _center = center;
        _radius = radius;
    }

    /**
     * getter of the center point
     *
     * @return the center point
     */
    public Point3D getCenter() {
        return _center;
    }

    /**
     * getter of the radius of the sphere
     *
     * @return the radius of the sphere
     */
    public double getRadius() {
        return _radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                ", _radius=" + _radius +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point) {
        Vector O_P = point.subtract(_center);
        return O_P.normalize();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }

    /**
     * implements the method of find Geo intersections for Sphere
     * @param ray
     * @param maxDistance the distance from the light
     * @return
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        Point3D P0 = ray.getP0();
        Vector v = ray.getDir();
        if (P0.equals(_center)) {
            return List.of(new GeoPoint(this, _center.add(v.scale(_radius))));
        }
        Vector u = _center.subtract(P0);

        double tm = alignZero(v.dotProduct(u));

        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));
        //no intersections the ray direction is above sphere
        if (d > _radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(_radius * _radius - d * d));

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        //in case the ray tangent to the sphere and it mean that th=0
        if (t1 == t2) {
            return null;
        }

        //2 intersections points
        if (t1 > 0 && t2 > 0 && alignZero(t1 - maxDistance) <= 0 && alignZero(t2 - maxDistance) <= 0) {
            Point3D p1 = P0.add(v.scale(t1));
            Point3D p2 = P0.add(v.scale(t2));

            return List.of(new GeoPoint(this, p1), new GeoPoint(this, p2));
        }
        //one intersection point
        if (t1 > 0&&alignZero(t1 - maxDistance) <= 0) {
            // Point3D p1 = P0.add(v.scale(t1));
            //refactoring
            Point3D p1 = ray.getPoint(t1);
            return List.of(new GeoPoint(this, p1));
        }
        //one intersection point
        if (t2 > 0&&alignZero(t2 - maxDistance) <= 0) {
            //Point3D p2 = P0.add(v.scale(t2));
            //refactoring
            Point3D p2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this, p2));
        }
        return null;
    }
}
