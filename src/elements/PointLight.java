package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource {
    private Point3D _position;
    private double _kC;
    private double _kL;
    private double _kQ;

    /**
     * constructor
     *
     * @param intensity
     * @param position
     * @param kC        Discount coefficients
     * @param kL        Discount coefficients
     * @param kQ        Discount coefficients
     */
    public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ) {
        super(intensity);
        _position = position;
        _kC = kC;
        _kL = kL;
        _kQ = kQ;
    }

    /**
     * function that calculate the intensity at the point where the point light arrived
     *
     * @param p the point of the shape the light arrived
     * @return the intensity
     */
    @Override
    public Color getIntensity(Point3D p) {
        double d = _position.distance(p);
        return (this.getIntensity()).scale(1d / _kC + _kL * d + _kQ * d * d);
    }

    /**
     * function to find the direction of the light that went to from the
     * point light to the shape
     *
     * @param p
     * @return
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position);
    }
}
