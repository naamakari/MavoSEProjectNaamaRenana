package geometries;

import primitives.Ray;

/**
 * class that implements geometry interface and extends from tube
 * because it is have a height ant it is not infinity
 */
public class Cylinder extends Tube implements Geometry{
    double _height;

    //constructor
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

}
