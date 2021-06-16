package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * class for scene, contain the geometries, the light and the background of the scene
 */
public class Scene {
    public String _name;
    public Color _background = Color.BLACK;
    public AmbientLight _ambientLight = new AmbientLight(Color.BLACK, 0d);
    public Geometries _geometries;
    public List<LightSource> _lights=new LinkedList<LightSource>();

    /**
     * constructor
     * @param name the name of the scene
     */
    public Scene(String name) {
        _name = name;
        _geometries= new Geometries();
    }

    /**
     * setter for the background, builder pattern
     * @param background the background of the scene
     * @return the scene
     */
    public Scene setBackground(Color background) {
        _background = background;
        return this;
    }

    /**
     * setter for the ambient light, builder pattern
     * @param ambientLight the ambient light of the scene
     * @return the scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        _ambientLight = ambientLight;
        return this;
    }

    /**
     * setter for the geometries, builder pattern
     * @param geometries the geometries of the scene
     * @return the scene
     */
    public Scene setGeometries(Geometries geometries) {
        _geometries = geometries;
        return this;
    }

    /**
     * setter for the lights,builder pattern
     * @param lights the lights of the scene
     * @return  the scene
     */
    public Scene setLights(List<LightSource> lights) {
        _lights = lights;
        return this;
    }

}
