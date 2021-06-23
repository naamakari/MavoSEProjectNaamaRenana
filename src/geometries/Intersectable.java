package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

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
    class Box {
        private Point3D _upRightBehind;//the up right point
        private Point3D _downLeftFront;//the down left point
        private Point3D _center;//the center point, for the distance between the boxes

        /**
         * constructor
         * @param upRightBehind
         * @param downLeftFront
         * @param center
         */
        public Box(Point3D upRightBehind, Point3D downLeftFront, Point3D center) {
            _upRightBehind = upRightBehind;
            _downLeftFront = downLeftFront;
            _center = center;
        }

        /**
         * default constructor
         */
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

        public boolean isIntersectBox(Ray ray){
            Point3D P0=ray.getP0();
            double xP0=P0.getX();
            double yP0=P0.getY();
            double zP0=P0.getZ();

            Vector dir=ray.getDir();
            double xDir=dir.getHead().getX();
            double yDir=dir.getHead().getY();
            double zDir=dir.getHead().getZ();

            double xMin=_upRightBehind.getX();
            double yMin=_upRightBehind.getY();
            double zMin=_upRightBehind.getZ();
            double xMax=_downLeftFront.getX();
            double yMax=_downLeftFront.getY();
            double zMax=_downLeftFront.getZ();

            double tMinX,tMaxX;

            if(xDir>=0){//the value x of the vector is positive
                tMinX=(xMin-xP0)/xDir;
                tMaxX=(xMax-xP0)/xDir;
            }
            else{
                tMinX=(xMax-xP0)/xDir;
                tMaxX=(xMin-xP0)/xDir;
            }

            double tMinY,tMaxY;

            if(yDir>=0){
                tMinY=(yMin-yP0)/yDir;
                tMaxY=(yMax-yP0)/yDir;
            }
            else{
                tMinY=(yMax-yP0)/yDir;
                tMaxY=(yMin-yP0)/yDir;
            }

            if((tMinX>tMaxY)||(tMinY>tMaxX)){
                return false;
            }

            if(tMinY>tMinX){//????
                tMinX=tMinY;
            }
            if(tMaxY<tMaxX){
                tMaxX=tMaxY;
            }

            double tMinZ,tMaxZ;

            if(zDir>=0){
                tMinZ=(zMin-zP0)/zDir;
                tMaxZ=(zMax-zP0)/zDir;
            }
            else{
                tMinZ=(zMax-zP0)/zDir;
                tMaxZ=(zMin-zP0)/zDir;
            }

            if((tMinX>tMaxZ)||(tMinZ>tMaxX)){
                return false;
            }

            if(tMinZ>tMinX){
                tMinX=tMinZ;
            }
            if(tMaxZ<tMaxX){
                tMaxX=tMaxZ;
            }
            return true;
        }

    }

}
