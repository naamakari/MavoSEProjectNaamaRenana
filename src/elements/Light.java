package elements;

import primitives.Color;

/**
 * class for all the types of the light
 */
abstract class Light {
   final protected Color _intensity;

    /**
     * constructor
     * @param intensity the intensity of the light
     */
    protected Light(Color intensity) {
        _intensity = intensity;
    }

    /**
     * getter for the intensity
     * @return the intensity
     */
    public Color getIntensity() {
        return _intensity;
    }
}
