package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class for spot light that extend from point light
 */
public class SpotLight extends PointLight {
    private Vector _direction;

    /**
     * constructor
     * @param intensity
     * @param position
  //   * @param kC        Discount coefficients
    // * @param kL        Discount coefficients
    // * @param kQ        Discount coefficients
     * @param direction
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        _direction = direction;
    }

    /**
     * function that calculate the intensity at the point where the point light arrived
     * @param p the point of the shape the light arrived
     * @return the intensity
     */
    @Override
    public Color getIntensity(Point3D p) {
        double factor = alignZero(Math.max(0, _direction.dotProduct(getL(p))));

        if (!isZero(factor)) {
            return super.getIntensity(p).scale(factor);
        }
        throw new IllegalArgumentException("the angle equals to 0");
    }

    /**
     * function to find the direction of the light that went to from the
     * spot light to the shape
     * @param p
     * @return
     */
    @Override
    public Vector getL(Point3D p) {
        return super.getL(p).normalized();
    }
}
