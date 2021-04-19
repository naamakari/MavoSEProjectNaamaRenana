package elements;

import primitives.Color;

/**
 * class for ambient light, containing the intensity of the light
 */
public class AmbientLight {
    Color _intensity;

    /**
     * constructor for calculating the intensity
     * @param IA
     * @param KA
     */
    public AmbientLight(Color IA,double KA) {
        _intensity=IA.scale(KA);
    }

    public Color getIntensity() {
        return _intensity;
    }
}
