package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * class that represent all the point that are at the side of the point that received
 */
public class Ray {
    private static final double DELTA = 0.1;//constant, accuracy for shifting First Rays
    final Point3D _p0;
    final Vector _dir;

    /**
     * constructor of 2 parameters
     * @param p0
     * @param dir
     */

    public Ray(Point3D p0, Vector dir) {
        _dir = dir.normalize();//normalize the direction vector
        _p0 = p0;
    }

    /**
     * constructor of 3 parameters
     * @param point
     * @param direction
     * @param n
     */
    public Ray(Point3D point, Vector direction, Vector n) {
        Vector delta = n.scale(n.dotProduct(direction) > 0 ? DELTA : -DELTA);
        _p0 = point.add(delta);
        _dir = direction.normalized();
    }

    /**
     * getter for origin of the Ray
     *
     * @return p0
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * getter for direction vector of the Ray
     *
     * @return dir
     */
    public Vector getDir() {
        return new Vector(_dir._head);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray._p0) && _dir.equals(ray._dir);
    }

    @Override
    public String toString() {
        return "p(0,0): " + _p0 + ", direction:" + _dir;
    }

    public Point3D getPoint(double t) {
        Point3D p = getP0().add(getDir().scale(t));
        return p;
    }

    /**
     * function to find the closest point to the ray
     *
     * @param point3DList the list of the points
     * @return the closest point to the start of the ray
     */
    public Point3D findClosestPoint(List<Point3D> point3DList) {
        if (point3DList == null || point3DList.size() == 0) {
            return null;
        }
        Point3D minPoint = point3DList.get(0);
        double minDistance = _p0.distance(point3DList.get(0));

        for (int i = 1; i < point3DList.size(); i++) {
            double distance = _p0.distance(point3DList.get(i));
            if (distance < minDistance) {
                minDistance = distance;
                minPoint = point3DList.get(i);
            }
        }
        return minPoint;
    }

    /**
     * function that get the closest points to ray
     *
     * @param GeoPointList
     * @return
     */
    public GeoPoint getClosestGeoPoint(List<GeoPoint> GeoPointList) {
        if (GeoPointList == null || GeoPointList.size() == 0) {
            return null;
        }
        GeoPoint minPoint = GeoPointList.get(0);
        double minDistance = _p0.distance(GeoPointList.get(0)._point);

        for (int i = 1; i < GeoPointList.size(); i++) {
            double distance = _p0.distance(GeoPointList.get(i)._point);
            if (distance < minDistance) {
                minDistance = distance;
                minPoint = GeoPointList.get(i);
            }
        }
        return minPoint;
    }

}
