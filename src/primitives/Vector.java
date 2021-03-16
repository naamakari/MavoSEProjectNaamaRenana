package primitives;

import static primitives.Point3D.ZERO;

public class Vector {
    Point3D _head;

    public Point3D getHead() {
        return _head;
    }

    public Vector(Point3D head) {
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        }

        _head = head;
    }

    public Vector(double x, double y, double z) {
   //     Point3D head = new Point3D(x, y, z);
     //   if (ZERO.equals(head)) {
       //     throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)")
        //}
        //_head = head;
        this(new Point3D(x,y,z));
    }

    public Vector crossProduct(Vector v) {
        double u1=_head._x._coord;
        double u2=_head._y._coord;
        double u3=_head._z._coord;

        double v1=v._head._x._coord;
        double v2=v._head._y._coord;
        double v3=v._head._z._coord;

        return new Vector(
                u2*v3-u3*v2,
                u3*v1-u1*v3,
                u1*v2-u2*v1
    );
    }

    public double dotProduct(Vector v) {
        double x=_head._x._coord*v._head._x._coord;
        double y=_head._y._coord*v._head._y._coord;
        double z=_head._z._coord*v._head._z._coord;

        return x+y+z;
    }
}
