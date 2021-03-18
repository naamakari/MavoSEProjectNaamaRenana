package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * class that implements geometry interface
 * contain axis ray and radius
 */
public class Tube implements Geometry{
    Ray _axisRay;
    double _radius;

    //constructor
    public Tube(Ray axisRay, double radius) {
        _axisRay = axisRay;
        _radius = radius;
    }

    /**
     * getter for axis ray
     * @return axis ray
     */
    public Ray getAxisRay() {
        return _axisRay;
    }

    /**
     * getter for the radius
     * @return the radius
     */
    public double getRadius() {
        return _radius;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "_axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
