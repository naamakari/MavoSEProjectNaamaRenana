package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class for spot light that extend from point light
 * have direction in addition to the point light
 */
public class SpotLight extends PointLight {
    private Vector _direction;

    /**
     * constructor
     * @param intensity the color of the light
     * @param position the start point of the light
     * @param direction the direction of the light
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        _direction = direction.normalized();
    }

    /**
     * function that calculate the intensity at the point where the point light arrived
     * @param p the point of the shape the light arrived
     * @return the intensity of the light
     */
    @Override
    public Color getIntensity(Point3D p) {
        Color intensity=super.getIntensity(p);//send to point light
        double f=alignZero(_direction.dotProduct(getL(p)));
        return intensity.scale(Math.max(0,f));
    }

}
