package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public interface Intersectable {

    /**
     * class PDS- Contains a type consisting of a Geometry and a Point3D
     */
    public static class GeoPoint {
        public Geometry _geometry;
        public Point3D _point;

        /**
         * constructor get 2 parameters
         * @param geometry the geometry
         * @param point the point of the geometry
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            _geometry = geometry;
            _point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return _geometry.equals(geoPoint._geometry) && _point.equals(geoPoint._point);
        }
    }

    /** function that return all the points that intersected the current geometry with specific ray
     * @param ray the specific ray
     * @return list of the intersection points
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp._point).collect(Collectors.toList());
    }

    /**
     * function that return all the points that intersected the current geometry and get 2 parameters
     * @param ray the specific ray we choose the intersection with
     * @param maxDistance the distance from the light
     * @return list of the intersection geoPoints
     */
    List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);

    /**
     * default function for 1 parameter
     * function that return all the points that intersected the current geometry with specific ray
     * @param ray the specific ray we choose the intersection with
     * @return list of the intersection geoPoints
     */
    default List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }
}
