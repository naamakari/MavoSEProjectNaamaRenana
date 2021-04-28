package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

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
     * @param p
     * @return
     */
    @Override
    public Color getIntensity(Point3D p) {
       return this.getIntensity();
    }

    /**
     * return the vector -the value of the direction of light
     * @param p
     * @return
     */
    @Override
    public Vector getL(Point3D p) {
        return this._direction;
    }
}
