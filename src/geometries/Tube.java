package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class that implements geometry interface
 * contain axis ray and radius
 */
public class Tube extends Geometry{
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
    public Vector getNormal(Point3D p) {
        Point3D p0=_axisRay.getP0();
        Vector v=_axisRay.getDir();
        Vector P0_P=p.subtract(p0);

        double t=alignZero(v.dotProduct(P0_P));

        /**
         * if the length is zero it is mean that p0 equals to p
         */
        if(isZero(t)){
            return P0_P.normalize();
        }

        Point3D O=p0.add(v.scale(t));
        if (O.equals(p)){
            throw new IllegalArgumentException("point p cannot be on the tube's axis");
        }
        return p.subtract(O).normalize();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }

    /**
     * (not) implements the method of find Geo intersections for tube
     * @param ray
     * @param maxDistance the distance from the light
     * @return
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray,double maxDistance) {
        return null;
    }
}
