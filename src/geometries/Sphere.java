package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * class that implements geometry interface
 * contain point of the center of the sphere and the radius
 */
public class Sphere implements Geometry {
    Point3D _center;
    double _radius;

    //constructor
    public Sphere(Point3D center, double radius) {
        _center = center;
        _radius = radius;
    }

    /**
     * getter of the center point
     * @return the center point
     */
    public Point3D getCenter() {
        return _center;
    }

    /**
     * getter of the radius of the sphere
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
        return null;
    }
}
