package primitives;

/**
 * class for the material of the objects
 */
public class Material {
    public double _kD =0d;
    public double _kS =0d;
    public int _nShininess =0;

    //setter for kD
    public Material setKd(double kD) {
       _kD = kD;
        return this;
    }

    //setter for kS
    public Material setKs(double kS) {
        _kS = kS;
        return this;
    }

    //setter for nShininess
    public Material setnShininess(int nShininess) {
       _nShininess = nShininess;
       return this;
    }
}
