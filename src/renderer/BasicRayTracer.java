package renderer;

import geometries.Intersectable.*;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * class that inheritor the abstract class RayTracerBase
 */
public class BasicRayTracer extends RayTracerBase{

    /**
     * constructor, use the father
     * @param scene
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * implements of the abstract function
     * @param ray the ray that passed
     * @return the color
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersectionsList=_scene._geometries.findGeoIntersections(ray);
        if(intersectionsList==null){
            return _scene._background;
        }
        GeoPoint closestPoint=ray.getClosestGeoPoint(intersectionsList);
        return calColor(closestPoint);
    }

    /**
     * function that calculate the color of point
     * @param geoPoint the point we want to calculate the color for
     * @return the color the function found
     */
    public Color calColor(GeoPoint geoPoint){
        return _scene._ambientLight.getIntensity().add(geoPoint._geometry.getEmission());

    }

}
