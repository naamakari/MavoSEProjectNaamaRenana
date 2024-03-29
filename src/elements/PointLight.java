package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.alignZero;


/**
 * class for the point light that implements light source and inherits from light class
 */
public class PointLight extends Light implements LightSource {
    private final Point3D _position;
    private double _kC = 1d;
    private double _kL = 0d;
    private double _kQ = 0d;

    /**
     * constructor
     * @param intensity the intensity of the light
     * @param position the start point of the Point light
     */
    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        _position = position;
    }

    /**
     * function that calculate the intensity at the point where the point light arrived
     * @param p the point of the shape the light arrived
     * @return the intensity of the light
     */
    @Override
    public Color getIntensity(Point3D p) {
        double d = alignZero(_position.distance(p));
        return _intensity.scale(1d / (_kC + _kL * d + _kQ * d * d));
    }

    /**
     * function to find the direction of the light that went to from the
     * point light to the shape
     * @param p the point of the intersection on the shape
     * @return vector of the direction of the point light
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalized();
    }

    /**
     * function to get the distance for the Point light after calculate
     * @param point the point of the intersection with the geometry
     * @return the distance between the start point of the light to the intersection point
     */
    @Override
    public double getDistance(Point3D point) {
        return _position.distance(point);
    }

    /**
     * setter for the kc factor
     * @param kC Discount factor from the formula, Multiplied by nothing
     * @return Point light, like builder pattern
     */
    public PointLight setKc(double kC) {
        _kC = kC;
        return this;
    }

    /**
     * setter for the kl factor
     * @param kL Discount factor from the formula, Multiplied by the distance
     * @return Point light, like builder pattern
     */
    public PointLight setKl(double kL) {
        _kL = kL;
        return this;
    }

    /**
     * setter for the kq factor
     * @param kQ Discount factor from the formula, Multiplied by square distance
     * @return Point light, like builder pattern
     */
    public PointLight setKq(double kQ) {
        _kQ = kQ;
        return this;
    }
}
