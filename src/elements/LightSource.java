package elements;


import primitives.*;

/**
 * interface for the source of the light
 */
public interface LightSource {

    /**
     * getter of intensity of the light
     * @param p the point of the intersection with some geometry
     * @return the intensity
     */
    public Color getIntensity(Point3D p);

    /**
     * return vector that touch the object
     * @param p the point of the intersection
     * @return the vector of the light
     */
    public Vector getL(Point3D p);

    /**
     * function to get the distance for the each light
     * @param point the point of the intersect with the geometry
     * @return the distance
     */
    double getDistance(Point3D point);
}
