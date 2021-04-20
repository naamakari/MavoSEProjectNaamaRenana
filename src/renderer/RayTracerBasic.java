package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * class that inheritor the abstract class RayTracerBase
 */
public class RayTracerBasic extends RayTracerBase{

    /**
     * constructor, use the father
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * implements of the abstract function
     * @param ray the ray that passed
     * @return the color
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Point3D> intersectionsList=_scene._geometries.findIntersections(ray);
        if(intersectionsList==null){
            return _scene._background;
        }
        Point3D closestPoint=ray.findClosestPoint(intersectionsList);
        return calColor(closestPoint);


    }

    /**
     * function that calculate the color of point
     * @param point3D the point we want to calculate the color for
     * @return the color the function found
     */
    public Color calColor(Point3D point3D){
        return _scene._ambientLight.getIntensity();
    }

}
