package geometries;

import primitives.Point3D;
import primitives.Ray;
import java.util.List;

/**
 *
 */
public interface Intersectable {

    /**
     *
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /**
         *
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }
    }
    /**
     *
     * @param ray
     * @return
     */
    List<Point3D> findIntersections(Ray ray);
    List<GeoPoint> findGeoIntersections(Ray ray);
}
