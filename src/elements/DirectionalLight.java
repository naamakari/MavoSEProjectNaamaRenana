package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class for directional light that inherits light class and implements light source interface
 */
public class DirectionalLight extends Light implements LightSource {
private Vector _direction;

    /**
     * constructor
     * @param intensity
     * @param direction
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction;
    }

    /**
     * function that return the intensity, in directional light its the intensity of the light
     * @param p the point we calculate the intensity for
     * @return the color of the point
     */
    @Override
    public Color getIntensity(Point3D p) {
       return this.getIntensity();
    }

    /**
     * return the vector -the value of the direction of light
     * @param p the point the light arrive to
     * @return the vector of the light
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction.normalized();
    }

    /**
     * function to get the distance for the directional light (infinity)
     * @param point the point the light arrived to
     * @return the distance- infinity
     */
    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }
}
