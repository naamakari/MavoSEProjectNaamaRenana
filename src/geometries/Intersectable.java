package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public interface Intersectable {

    //box of every geometries
    //  Box _box=new Box(new Point3D(0,0,0),new Point3D(0,0,0),new Point3D(0,0,0));


    /**
     * class PDS- Contains a type consisting of a Geometry and a Point3D
     */
    public static class GeoPoint {
        public Geometry _geometry;
        public Point3D _point;

        /**
         * constructor get 2 parameters
         *
         * @param geometry the geometry
         * @param point    the point of the geometry
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

    /**
     * function that return all the points that intersected the current geometry with specific ray
     *
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
     *
     * @param ray         the specific ray we choose the intersection with
     * @param maxDistance the distance from the light
     * @return list of the intersection geoPoints
     */
    List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);

    /**
     * default function for 1 parameter
     * function that return all the points that intersected the current geometry with specific ray
     *
     * @param ray the specific ray we choose the intersection with
     * @return list of the intersection geoPoints
     */
    default List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    Box getBox();

    /**
     * return the distance between 2 intersectables's centers
     *
     * @param I
     * @param J
     * @return the distance
     */
    default double distanceBetweenBoxes(Intersectable I, Intersectable J) {
        Box box1 = I.getBox();
        Box box2 = J.getBox();
        return box1.getCenter().distance(box2.getCenter());

    }

    /**
     * class for box of every intersectable for BVH
     */
    public class Box {
        private Point3D _upRightBehind;//the up right point
        private Point3D _downLeftFront;//the down left point
        private Point3D _center;//the center point, for the distance between the boxes

        public Box(Point3D upRightBehind, Point3D downLeftFront, Point3D center) {
            _upRightBehind = upRightBehind;
            _downLeftFront = downLeftFront;
            _center = center;
        }

        public Box() {
            _upRightBehind = new Point3D(0, 0, 0);
            _downLeftFront = new Point3D(0, 0, 0);
            _center = new Point3D(0, 0, 0);
        }

        //getters
        public Point3D getUpRightBehind() {
            return _upRightBehind;
        }

        public Point3D getDownLeftFront() {
            return _downLeftFront;
        }

        public Point3D getCenter() {
            return _center;
        }

        //setters
        public void setUpRightBehind(Point3D upRightBehind) {
            _upRightBehind = upRightBehind;
        }

        public void setDownLeftFront(Point3D downLeftFront) {
            _downLeftFront = downLeftFront;
        }

        public void setCenter(Point3D center) {
            _center = center;
        }

    }

}
