package primitives;

import java.util.Objects;

/**
 * class to represent point at the area
 */
public class Point3D {
    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;

    public final static Point3D ZERO = new Point3D(0d, 0d, 0d);

    ///constructor
    public Point3D(double x, double y, double z) {
        //  this(new Coordinate(x),new Coordinate(y),new Coordinate(z));

        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);

    }

    public double getX() {
        return _x._coord;
    }

//    public double getY() {
//        return _y._coord;
//    }
//
//    public double getZ() {
//        return _z._coord;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }

    @Override
    public String toString() {
        return "(" + _x + ", " + _y + ", " + _z + ')';
    }

    /**
     * function that make sub between 2 vectors
     *
     * @param pt2 the point at origin of vector
     * @return new Vector
     */
    public Vector subtract(Point3D pt2) {
        Point3D head = new Point3D(
                _x._coord - pt2._x._coord,
                _y._coord - pt2._y._coord,
                _z._coord - pt2._z._coord
        );
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        }
        return new Vector(head);
    }

    /**
     * function that add vector to point
     *
     * @param vec the vector we add
     * @return the origin point plus the vector
     */
    public Point3D add(Vector vec) {
        Point3D newPoint = new Point3D(
                _x._coord + vec._head._x._coord,
                _y._coord + vec._head._y._coord,
                _z._coord + vec._head._z._coord
        );
        // if (ZERO.equals(newPoint)) {
        //   throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        //}
        return newPoint;
    }

    /**
     * function that calculate the distance between 2 points
     *
     * @param po the second point in 3D
     * @return squared distance
     */
    public double distanceSquared(Point3D po) {
        return ((_x._coord - po._x._coord) * (_x._coord - po._x._coord)) +
                ((_y._coord - po._y._coord) * (_y._coord - po._y._coord)) +
                ((_z._coord - po._z._coord) * (_z._coord - po._z._coord));
    }

    /**
     * euclidean distance
     *
     * @param po2 the second point
     * @return distance
     */
    public double distance(Point3D po2) {
        return Math.sqrt(distanceSquared(po2));
    }

    /**
     * function that checks if points are at the same line
     * @param p1
     * @return
     */
    public boolean isSameLine(Point3D p1) {

        double d1 = (double) (p1._x._coord / _x._coord);
        if (p1._y._coord != _y._coord * d1 || p1._z._coord != _z._coord * d1) {
            //throw new IllegalArgumentException("point are on the same line");
            return false;
        }
        return true;
    }
}
