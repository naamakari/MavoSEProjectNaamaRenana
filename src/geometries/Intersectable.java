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

            double xMin=_downLeftFront.getX();
            double yMin=_downLeftFront.getY();
            double zMin=_downLeftFront.getZ();
            double xMax=_upRightBehind.getX();
            double yMax=_upRightBehind.getY();
            double zMax=_upRightBehind.getZ();

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

            if(tMinY>tMinX){
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

            return true;
     }

        /**
         * Recursive function that pass over the tree we build from the geometries and
         * check if the ray is pass over some box
         * @param geo
         * @param ray
         * @return
         */
        public boolean checkIntersectionaInTree(Geometries geo,Ray ray){
            //check if the ray is intersect the big box
            if (!geo._box.isIntersectBox(ray)){
                return false;
            }

            //if we arrived to leaf (left) and the ray is intersect his box
            if(geo._listGeometries.get(0) instanceof Geometry){
                if(geo._listGeometries.get(0).getBox().isIntersectBox(ray)) {
                    return true;
                }
                else{
                    if(geo._listGeometries.get(1) instanceof Geometries) {
                        return checkIntersectionaInTree((Geometries) geo._listGeometries.get(1), ray);
                    }
                    else{
                        return geo._listGeometries.get(1).getBox().isIntersectBox(ray);
                    }
                }
            }
            //if we arrived to leaf (right) and the ray is intersect his box
            if(geo._listGeometries.get(1) instanceof Geometry){
                if(geo._listGeometries.get(1).getBox().isIntersectBox(ray)) {
                    return true;
                }
                else{
                    if(geo._listGeometries.get(0) instanceof Geometries) {
                        return checkIntersectionaInTree((Geometries) geo._listGeometries.get(0), ray);
                    }
                    else{
                        return geo._listGeometries.get(0).getBox().isIntersectBox(ray);
                    }
                }
            }

            boolean leftSon= checkIntersectionaInTree((Geometries) geo._listGeometries.get(0),ray);
            if(leftSon){
                return true;
            }
            boolean rightSon= checkIntersectionaInTree((Geometries) geo._listGeometries.get(1),ray);
            if(rightSon){
                return true;
            }
            return false;

        }


    }

}
