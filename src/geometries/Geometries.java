package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * this class helps us to implements design pattern- composite
 */
public class Geometries implements Intersectable {

    List<Intersectable> _listGeometries=new LinkedList<>();

    /**
     * default constructor
     */
    public Geometries() {
        _listGeometries = new LinkedList<Intersectable>();
    }

    /**
     * parameter constructor
     * @param geometries
     */
    public Geometries(Intersectable... geometries) {
            add(geometries);

    }

    /**
     * function for add geometry to the list of the geometries
     * @param geometries
     */
    public void add(Intersectable... geometries) {
        for (int i = 0; geometries.length > i; i++) {
            _listGeometries.add(geometries[i]);
        }
    }


    /**
     * function that return all the gePoints that intersected the current geometry
     * @param ray
     * @param maxDistance
     * @return  list of a geoPoints3D
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray,double maxDistance) {
        List<GeoPoint> intersections = null;
        for (Intersectable geometry : _listGeometries) {
            List<GeoPoint> geoIntersections = geometry.findGeoIntersections(ray,maxDistance);
            //if the list is not empty
            if (geoIntersections != null) {
                //if it is the first time to add intersection
                if(intersections==null){
                    intersections=new LinkedList<>();
                }
                intersections.addAll(geoIntersections);
            }
            // if there are elements in geoIntersections – add them to intersections
        }
        return intersections;
    }

}
