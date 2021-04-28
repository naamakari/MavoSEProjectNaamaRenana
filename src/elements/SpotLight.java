package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class SpotLight extends PointLight{
    private Vector _direction;

    /**
     * constructor
     * @param intensity
     * @param position
     * @param kC Discount coefficients
     * @param kL Discount coefficients
     * @param kQ Discount coefficients
     * @param direction
     */
    public SpotLight(Color intensity, Point3D position, double kC, double kL, double kQ, Vector direction) {
        super(intensity, position, kC, kL, kQ);
        _direction = direction;
    }
}
