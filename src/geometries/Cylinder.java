package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * class that implements geometry interface and extends from tube
 * because it is have a height ant it is not infinity
 */
public class Cylinder extends Tube{
    double _height;

    /**
     * constructor
     * @param axisRay the axis ray
     * @param radius radius of the Cylinder
     * @param height the height of the Cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        _height = height;
    }

    /**
     * getter for the height
     * @return the height
     */
    public double getHeight() {
        return _height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "_height=" + _height +
                ", _axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }

    /**
     * bonus we did not do
     * @param ray
     * @return
     */
//    @Override
//    public Vector getNormal(Point3D p) {
//        return super.getNormal(p);
//    }

    /**
     * function to find the intersection points with specific ray
     * @param ray the specific ray we check the intersection with the Cylinder
     * @return list of all intersection points
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return super.findIntersections(ray);
    }
}
