package renderer;

import elements.LightSource;
import geometries.Intersectable.*;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class that inheritor the abstract class RayTracerBase
 */
public class BasicRayTracer extends RayTracerBase {

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
        List<GeoPoint> intersectionsList = _scene._geometries.findGeoIntersections(ray);
        if (intersectionsList == null) {
            return _scene._background;
        }
        GeoPoint closestPoint = ray.getClosestGeoPoint(intersectionsList);
        return calColor(closestPoint,ray);
    }

    /**
     * function that calculate the color of point
     * @param geoPoint the point we want to calculate the color for
     * @return the color the function found
     */
    public Color calColor(GeoPoint geoPoint, Ray ray) {
        return _scene._ambientLight.getIntensity()
                .add(geoPoint._geometry.getEmission()
                        //add calculated light contribution from all light sources)
                        .add(calcLocalEffects(geoPoint, ray)));

    }

    /**
     * A function that calculates the diffusion and speculator for each object
     * @param intersection
     * @param ray
     * @return
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();//the vector of the ray
        Vector n = intersection._geometry.getNormal(intersection._point);//get the normal of the geometry
        double nv = alignZero(n.dotProduct(v));
        if (isZero(nv)) {//the vectors are orthogonal each other
            return Color.BLACK;
        }
        Material material = intersection._geometry.getMaterial();
        int nShininess = material._nShininess;
        double kd = material._kD;
        double ks = material._kS;
        Color color =Color.BLACK;
        for (LightSource lightSource : _scene._lights) {
            Vector l = lightSource.getL(intersection._point);//get the vector l of light source
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(intersection._point);//get the intensity of the geometry at the point
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),//calculates the diffusion
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));//calculates the speculator
            }
        }
        return color;
    }

    /**
     * help function that calculates the speculator for each object
     * @param ks The discount factor of the speculator
     * @param l the vector from the light source
     * @param n the normal of the object
     * @param v the vector from the camera
     * @param nShininess the value of the shininess of the material
     * @param lightIntensity the intensity of the light
     * @return
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r=l.subtract(n.scale(l.dotProduct(n)*2));
        double minus_vr=alignZero(v.dotProduct(r)*-1);
        return lightIntensity.scale(ks*Math.pow(Math.max(0,minus_vr),nShininess));
    }

    /**
     * help function that calculates the diffusion for each object
     * @param kd Diffusion coefficient of diffusion
     * @param l the vector from the light source
     * @param n the normal of the object
     * @param lightIntensity  the intensity of the light
     * @return
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double factor=alignZero(Math.abs(l.dotProduct(n)));
        return lightIntensity.scale(kd*factor);
    }
}