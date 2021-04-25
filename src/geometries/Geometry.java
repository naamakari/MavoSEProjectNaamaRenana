package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface of all the geometry
 */
public abstract class Geometry implements Intersectable{
    protected Color _emission=Color.BLACK;

    public Color getEmission() {
        return _emission;
    }

    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }

    /**
     * function that calculate the normal of every geometry
     * @param point
     * @return
     */
   public abstract Vector getNormal(Point3D point);
}
