package renderer;

import elements.LightSource;
import geometries.Intersectable.*;
import primitives.*;
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
     * help function to do the sigma that exist at the Phong model
     * @param lightSourceList
     * @param geoPoint
     * @return
     */
    public double sumPhong(List<LightSource> lightSourceList, GeoPoint geoPoint){
        double sigma=0d;
        double kd;
        double ks;
        Vector l;
        Vector n;
       // Color iL;

        for (int i = 0; i < lightSourceList.size(); i++) {
            kd=geoPoint._geometry.getMaterial().kD;
            ks=geoPoint._geometry.getMaterial().kS;
            l=lightSourceList.get(i).getL(geoPoint._point);
            n=geoPoint._geometry.getNormal(geoPoint._point);
        }
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
