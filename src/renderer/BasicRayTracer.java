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

    private static final int MAX_CALC_COLOR_LEVEL = 10;//Stop condition that represents the number of levels we want to enter in the recursion
    private static final double MIN_CALC_COLOR_K = 0.001;//Stop conditions that make sure there are no more unnecessary recursions
    private static final double INITIAL_K = 1.0;

    /**
     * constructor, use the father
     *
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
//        List<GeoPoint> intersectionsList = _scene._geometries.findGeoIntersections(ray);
//        if (intersectionsList == null) {
//            return _scene._background;
//        }
//        GeoPoint closestPoint = ray.getClosestGeoPoint(intersectionsList);
//        return calColor(closestPoint, ray);

        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? _scene._background : calcColor(closestPoint, ray);
    }

    /**
     * recursive function for calculate the color of the point
     * @param geoPoint
     * @param ray
     * @return
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene._ambientLight.getIntensity());
    }


    /**
     * function that calculate the global effects
     * @param intersection the geoPoint of the intersection
     * @param v
     * @param level
     * @param k
     * @return
     */
    private Color calcGlobalEffects(GeoPoint intersection, Vector v, int level, double k) {
        Color color = Color.BLACK;
        Vector n = intersection._geometry.getNormal(intersection._point);
        Material material = intersection._geometry.getMaterial();
        double kkr = k * material._kR;
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(calcReflection(n, v,intersection), level, material._kR, kkr);
        double kkt = k * material._kT;
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(
                    calcGlobalEffect(calcRefraction(intersection, v), level, material._kT, kkt));
        return color;
    }

    /**
     * the function that send from calcGlobalEffects function.
     * @param ray
     * @param level
     * @param kX
     * @param kkX
     * @return
     */
    private Color calcGlobalEffect(Ray ray, int level, double kX, double kkX) {
            GeoPoint gp = findClosestIntersection(ray);
            return (gp == null ? _scene._background : calcColor(gp, ray, level-1, kkX)
            ).scale(kX);
    }
    /**
     * function that calculate the color of point
     * @param geoPoint the point we want to calculate the color for
     * @return the color the function found
     */
    public Color calcColor(GeoPoint geoPoint, Ray ray,int level, double k) {
        Color color =geoPoint._geometry.getEmission();
        color = color.add(calcLocalEffects(geoPoint, ray,k));
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray.getDir(), level, k));
//        return _scene._ambientLight.getIntensity()
//                .add(geoPoint._geometry.getEmission()
//                        //add calculated light contribution from all light sources)
//                        .add(calcLocalEffects(geoPoint, ray)));
    }

    /**
     * A function that calculates the diffusion and speculator for each object
     * @param intersection
     * @param ray
     * @return
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray,double k) {
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
        Color color = Color.BLACK;
        for (LightSource lightSource : _scene._lights) {
            Vector l = lightSource.getL(intersection._point);//get the vector l of light source
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
              //  if (unshaded(lightSource, l, n, intersection)) {//checks the shadow
               double ktr = transparency(lightSource, l, n, intersection);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(intersection._point).scale(ktr);//get the intensity of the geometry at the point
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),//calculates the diffusion
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));//calculates the speculator
                }
            }
        }
        return color;
    }

    /**
     * A function that checks if there is a shadow on a particular point
     * @param light
     * @param l
     * @param n
     * @param intersection
     * @return
     */
    @Deprecated
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint intersection) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(intersection._point, lightDirection, n);
        List<GeoPoint> intersections = _scene._geometries
                .findGeoIntersections(lightRay, light.getDistance(intersection._point));
        if (intersections==null){
            return true;
        }
        double lightDistance=light.getDistance(intersection._point);
       for (GeoPoint gp: intersections){
           if(alignZero(gp._point.distance(intersection._point)-lightDistance)<=0&&
           gp._geometry.getMaterial()._kT==0)
               return false;
       }
        return true;
    }

    /**
     *  A function that checks if there is a transparency on a particular point
     * @param light
     * @param l
     * @param n
     * @param geoPoint
     * @return
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
       Point3D point=geoPoint._point;
        Ray lightRay = new Ray(point, lightDirection, n);
        double lightDistance = light.getDistance(point);
        var intersections = _scene._geometries.findGeoIntersections(lightRay,lightDistance);
        if (intersections == null) {
            return 1.0;
        }
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp._point.distance(point)-lightDistance) <= 0) {
                ktr *= gp._geometry.getMaterial()._kT;
                if (ktr < MIN_CALC_COLOR_K) return 0.0;
            }
        }
        return ktr;
    }
    /**
     * help function that calculates the speculator for each object
     * @param ks             The discount factor of the speculator
     * @param l              the vector from the light source
     * @param n              the normal of the object
     * @param v              the vector from the camera
     * @param nShininess     the value of the shininess of the material
     * @param lightIntensity the intensity of the light
     * @return
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));
        double minus_vr = alignZero(v.dotProduct(r) * -1);
        return lightIntensity.scale(ks * Math.pow(Math.max(0, minus_vr), nShininess));
    }

    /**
     * help function that calculates the diffusion for each object
     * @param kd             Diffusion coefficient of diffusion
     * @param l              the vector from the light source
     * @param n              the normal of the object
     * @param lightIntensity the intensity of the light
     * @return
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double factor = alignZero(Math.abs(l.dotProduct(n)));
        return lightIntensity.scale(kd * factor);
    }

    /**
     * function for calculate the reflection ray
     * @param n normal of the geometry
     * @param v the vector of the light
     * @param gp the point the light arrived
     * @return the ray of the reflection
     */
    private Ray calcReflection(Vector n, Vector v, GeoPoint gp) {
        Vector no=gp._geometry.getNormal(gp._point);
        Vector r=v.subtract(n.scale(v.dotProduct(n)*2));
        return new Ray(gp._point,r,no);
    }

    /**
     *  function for calculate the refraction ray
     * @param gp the point the light arrived
     * @param v the vector of the light
     * @return the ray of the refraction
     * we assume that the refraction index is 1
     */
    private Ray calcRefraction(GeoPoint gp,Vector v) {
        Vector n=gp._geometry.getNormal(gp._point);
        return new Ray(gp._point,v,n);
    }

    /**
     *refactoring of the function to find the closest intersection
     * @param ray
     * @return
     */
    private GeoPoint findClosestIntersection(Ray ray){
        return ray.getClosestGeoPoint(_scene._geometries.findGeoIntersections(ray));
    }


}