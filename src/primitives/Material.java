package primitives;

/**
 * class for the material of the objects
 */
public class Material {
    public double _kD =0d;//Diffusion
    public double _kS =0d;//Specular
    public int _nShininess =0;//shine
    public double _kT=0.0; //Promotes Refraction
    public double _kR=0.0; //Coefficient of reflection

    /**
     *  setter for kT
     * @param kT
     * @return
     */
    public Material setkT(double kT) {
        _kT = kT;
        return this;
    }

    /**
     *  setter for kR
     * @param kR
     * @return
     */
    public Material setkR(double kR) {
        _kR = kR;
        return this;
    }

    /**
     *  setter for kD
     */

    public Material setKd(double kD) {
       _kD = kD;
        return this;
    }

    /**
     * setter for kS
     * @param kS
     * @return
     */

    public Material setKs(double kS) {
        _kS = kS;
        return this;
    }

    /**
     * setter for nShininess
     * @param nShininess
     * @return
     **/
    public Material setnShininess(int nShininess) {
       _nShininess = nShininess;
       return this;
    }
}
