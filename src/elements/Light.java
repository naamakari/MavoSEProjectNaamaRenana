package elements;

import primitives.Color;

/**
 * class for all the types of the light
 */
abstract class Light {
    private Color _intensity;

    /**
     * constructor
     * @param intensity
     */
    protected Light(Color intensity) {
        _intensity = intensity;
    }

    /**
     * getter
     * @return
     */
    public Color getIntensity() {
        return _intensity;
    }
}
