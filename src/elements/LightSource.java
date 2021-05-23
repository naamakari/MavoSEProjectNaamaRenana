package elements;


import primitives.*;

/**
 * interface for the source of the light
 */
public interface LightSource {

    /**
     * getter of intensity of the light
     * @param p
     * @return
     */
    public Color getIntensity(Point3D p);

    /**
     * return vector that touch the object
     * @param p
     * @return
     */
    public Vector getL(Point3D p);

    /**
     * function to get the distance for the each light
     * @param point the point of the intersect with the geometry
     * @return the distance
     */
    double getDistance(Point3D point);
}
