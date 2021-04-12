package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface of all the geometry
 */
public interface Geometry extends Intersectable{
    /**
     * function that calculate the normal of every geometry
     * @param point
     * @return
     */
    Vector getNormal(Point3D point);
}
