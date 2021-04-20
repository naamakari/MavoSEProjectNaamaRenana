package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * class that help to return color of pixel that ray pass at it
 */
public abstract class RayTracerBase {
    protected Scene _scene;

    /**
     * constructor
     * @param scene the current scene
     */
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * the function that return the color
     * @param ray the ray that passed
     * @return the color
     */
     public abstract Color traceRay(Ray ray);
}
