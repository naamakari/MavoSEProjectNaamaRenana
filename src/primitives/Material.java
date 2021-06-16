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
     * setter for kT, like builder pattern
     * @param kT transparency
     * @return the material
     */
    public Material setkT(double kT) {
        _kT = kT;
        return this;
    }

    /**
     * setter for kR, like builder pattern
     * @param kR mirror or not
     * @return the material
     */
    public Material setkR(double kR) {
        _kR = kR;
        return this;
    }

    /**
     * setter for kD, like builder pattern
     * @param kD Diffusion
     * @return the material
     */

    public Material setKd(double kD) {
       _kD = kD;
        return this;
    }

    /**
     * setter for kS, like builder pattern
     * @param kS Specular
     * @return the material
     */

    public Material setKs(double kS) {
        _kS = kS;
        return this;
    }

    /**
     * setter for nShininess, like builder pattern
     * @param nShininess Shininess
     * @return the material
     **/
    public Material setnShininess(int nShininess) {
       _nShininess = nShininess;
       return this;
    }
}
