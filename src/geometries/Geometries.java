package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * this class helps us to implements design pattern- composite
 */
public class Geometries implements Intersectable {

    List<Intersectable> _listGeometries = new LinkedList<>();
    //field for box of couple of boxes. to union
    Box _box = new Box();

    /**
     * setter for the box
     *
     * @param box
     */
    public void setBox(Box box) {
        _box = box;
    }

    public Box getBox() {
        return _box;
    }

    /**
     * default constructor
     */
    public Geometries() {
        _listGeometries = new LinkedList<Intersectable>();
    }

    /**
     * parameter constructor
     *
     * @param geometries the geometries we send the constructor
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);

    }

    /**
     * function for add geometry to the list of the geometries
     *
     * @param geometries the geometries we want to add to the lost of the geometries
     */
    public void add(Intersectable... geometries) {
        for (int i = 0; i < geometries.length; i++) {//pass all over the list of the geometries
            _listGeometries.add(geometries[i]);
        }
    }


    /**
     * function that return all the gePoints that intersected the current geometry with specific ray
     *
     * @param ray         the specific ray we check the intersection with
     * @param maxDistance the max distance
     * @return list of a geoPoints3D
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;
        for (Intersectable geometry : _listGeometries) {
            List<GeoPoint> geoIntersections = geometry.findGeoIntersections(ray, maxDistance);
            //if the list is not empty
            if (geoIntersections != null) {
                //if it is the first time to add intersection
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                intersections.addAll(geoIntersections);
            }
            // if there are elements in geoIntersections – add them to intersections
        }
        return intersections;
    }

    public Box buildNewBox(Intersectable left, Intersectable right) {
        double minX = Math.min(left.getBox().getDownLeftFront().getX(), right.getBox().getDownLeftFront().getX());
        double minY = Math.min(left.getBox().getDownLeftFront().getY(), right.getBox().getDownLeftFront().getY());
        double minZ = Math.min(left.getBox().getDownLeftFront().getZ(), right.getBox().getDownLeftFront().getZ());
        double maxX = Math.max(left.getBox().getUpRightBehind().getX(), right.getBox().getUpRightBehind().getX());
        double maxY = Math.max(left.getBox().getUpRightBehind().getY(), right.getBox().getUpRightBehind().getY());
        double maxZ = Math.max(left.getBox().getUpRightBehind().getZ(), right.getBox().getUpRightBehind().getZ());
        Point3D p1 = new Point3D(minX, minY, minZ);
        Point3D p2 = new Point3D(maxX, maxY, maxZ);
        double centerX = maxX - minX / 2;
        double centerY = maxY - minY / 2;
        double centerZ = maxZ - minZ / 2;
        Point3D p3 = new Point3D(centerX, centerY, centerZ);
        return new Box(p1, p2, p3);
    }

    public void buildHierarchicalBVH() {
        //remove all the infinity geometries from the list
        List<Intersectable> inifinityGeometries=new LinkedList<>();
        for(Intersectable item: _listGeometries){
            if(item instanceof Plane|| item instanceof Tube){
                inifinityGeometries.add(item);
            }
        }
        _listGeometries.removeAll(inifinityGeometries);
        double distance;
        double minDistance;
        Intersectable left = null;
        Intersectable right = null;

        while (_listGeometries.size() > 1) {//while the list is not union to the hierarchical tree
            minDistance = Double.POSITIVE_INFINITY;
            for (Intersectable boxI : _listGeometries) {
                for (Intersectable boxJ : _listGeometries) {
                    if (boxI != boxJ && (distance = distanceBetweenBoxes(boxI, boxJ)) < minDistance) {//check the min value of the distance and save it
                        minDistance = distance;
                        left = boxI;
                        right = boxJ;
                    }
                }
            }
            //create new geometries for the childes
            Geometries leftRightGeometries = new Geometries(left, right);
            //define the box of the new geometries we create
            leftRightGeometries.setBox(buildNewBox(left, right));
            //delete the 2 intersectable from the list
            _listGeometries.remove(left);
            _listGeometries.remove(right);
            //add the new geometries we create to the list
            _listGeometries.add(leftRightGeometries);
        }

    }

}

