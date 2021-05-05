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

    public Scene(String name) {
        _name = name;
        _geometries= new Geometries();
    }

    //setter for the background
    public Scene setBackground(Color background) {
        _background = background;
        return this;
    }
//setter for the ambient light
    public Scene setAmbientLight(AmbientLight ambientLight) {
        _ambientLight = ambientLight;
        return this;
    }
//setter for the geometries
    public Scene setGeometries(Geometries geometries) {
        _geometries = geometries;
        return this;
    }

    //setter for the lights
    public Scene setLights(List<LightSource> lights) {
        _lights = lights;
        return this;
    }

}
