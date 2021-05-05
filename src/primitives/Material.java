package primitives;

/**
 * class for the material of the objects
 */
public class Material {
    public double kD=0d;
    public double kS=0d;
    public int nShininess=0;

    //setter for kD
    public void setkD(double kD) {
        this.kD = kD;
    }

    //setter for kS
    public void setkS(double kS) {
        this.kS = kS;
    }

    //setter for nShininess
    public void setnShininess(int nShininess) {
        this.nShininess = nShininess;
    }
}
