package elements;

import primitives.Color;

/**
 * class for ambient light, containing the intensity of the light
 */
public class AmbientLight extends Light{

    /**
     * constructor for calculating the intensity by the father constructor
     * @param IA
     * @param KA
     */
    public AmbientLight(Color IA,double KA) {
        super(IA.scale(KA));
    }

}
