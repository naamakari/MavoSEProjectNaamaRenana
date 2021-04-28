package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
private Point3D _position;
private double _kC;
private double _kL;
private double _kQ;

    /**
     * constructor
     * @param intensity
     * @param position
     * @param kC Discount coefficients
     * @param kL Discount coefficients
     * @param kQ Discount coefficients
     */
    public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ) {
        super(intensity);
        _position = position;
        _kC = kC;
        _kL = kL;
        _kQ = kQ;
    }

    @Override
    public Color getIntensity(Point3D p) {
        return (this.getIntensity())/_kC+_kL*d+_kQ*d*d;
    }

    @Override
    public Vector getL(Point3D p) {
        return null;
    }
}
