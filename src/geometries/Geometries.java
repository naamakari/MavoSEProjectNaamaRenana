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

    public Geometries(Intersectable... geometries) {
      // _listGeometries = new LinkedList<>();
       //for (int i = 0; geometries.length > i; i++) {
            add(geometries);

    }

    public void add(Intersectable... geometries) {
        for (int i = 0; geometries.length > i; i++) {
            _listGeometries.add(geometries[i]);
        }
    }

    /**
     * function that return all the points that intersected the current geometry
     * @param ray
     * @return list of a Point3Ds
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> intersections = null;
        for (int i = 0; _listGeometries.size() > i; i++) {
            List<Point3D> geometryPoints = _listGeometries.get(i).findIntersections(ray);
            //if the list is not empty
            if (geometryPoints != null) {
                //if it is the first time to add intersection
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                intersections.addAll(geometryPoints);
            }
        }
        //if the list is empty the list return null

        return intersections;
    }

    /**
     * function that return all the gePpoints that intersected the current geometry
     * @param ray
     * @return list of a geoPoints3D
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> intersections = null;
        for (Intersectable geometry : _listGeometries) {
            List<GeoPoint> geoIntersections = geometry.findGeoIntersections(ray);
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
