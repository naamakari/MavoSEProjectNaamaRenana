package primitives;

import java.util.Objects;

import static primitives.Point3D.ZERO;

/**
 * class that represents vector, set according tha last point
 */
public class Vector {
    Point3D _head;

    /**
     * getter for the vector
     * @return head
     */
    public Point3D getHead() {
        return _head;
    }

    /**
     *constructor for vector class
     * @param x double parameter
     * @param y double parameter
     * @param z double parameter
     */
    public Vector(double x, double y, double z) {
        Point3D head = new Point3D(x, y, z);
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        }
        _head = head;
        //this(new Point3D(x, y, z));
    }

    /**
     * constructor for vector class
     * @param head get head point
     */
    public Vector(Point3D head) {
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        }
        _head = head;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    @Override
    public String toString() {
        return "Vector:" + _head;
    }

    /**
     * function that do cross product between the origin and the vector the function received
     * @param v vector to do cross product with
     * @return new vector after the cross product
     */
    public Vector crossProduct(Vector v) {
        double u1 = _head._x._coord;
        double u2 = _head._y._coord;
        double u3 = _head._z._coord;

        double v1 = v._head._x._coord;
        double v2 = v._head._y._coord;
        double v3 = v._head._z._coord;

        return new Vector(
                u2 * v3 - u3 * v2,
                u3 * v1 - u1 * v3,
                u1 * v2 - u2 * v1
        );
    }

    /**
     * function that do dot product between the origin and the vector the function received
     * @param v vector to do dot product with
     * @return new vector after the dot product
     */
    public double dotProduct(Vector v) {
        double x = _head._x._coord * v._head._x._coord;
        double y = _head._y._coord * v._head._y._coord;
        double z = _head._z._coord * v._head._z._coord;

        return x + y + z;
    }

    /**
     * function that add vector to vector
     * @param vec the vector to add the origin vector
     * @return new vector after the adding
     */
    public Vector add(Vector vec) {
        return new Vector(_head._x._coord + vec._head._x._coord,
                _head._y._coord + vec._head._y._coord,
                _head._z._coord + vec._head._z._coord);

    }

    /**
     * function that subtract vector between vector
     * @param vec the vector to subtract the origin vector
     * @return new vector after the subtracting
     */
    public Vector subtract(Vector vec) {
        return new Vector(_head._x._coord - vec._head._x._coord,
                _head._y._coord - vec._head._y._coord,
                _head._z._coord - vec._head._z._coord);
    }

    /**
     * function that scale vector with num
     * @param num the scalar to scale with
     * @return new vector after the scale
     */
    public Vector scale(double num) {
        return new Vector(_head._x._coord * num,
                _head._y._coord * num,
                _head._z._coord * num);
    }

    /**
     * function that calculate the squared length of the vector
     * @return the squared length
     */
    public double lengthSquared() {
        return _head._x._coord * _head._x._coord +
                _head._y._coord * _head._y._coord +
                _head._z._coord * _head._z._coord;
    }

    /**
     * function that calculate the length of the vector
     * @return the length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * function that normalized the origin vector
     * @return the normlized vector(the origin vector)
     */
    public Vector normalize() {
        double len = length();
//
//        if (len == 0) {
//            throw new ArithmeticException("cannot divide by zero");
//        }

        double newX = _head._x._coord / len;
        double newY = _head._y._coord / len;
        double newZ = _head._z._coord / len;

        Point3D newPoint = new Point3D(newX, newY, newZ);

        //head vector cannot be point(0,0,0)
        if (ZERO.equals(newPoint)) {
            throw new IllegalArgumentException("head vector cannot be point(0,0,0)");
        }

        _head = newPoint;
        return this;
    }

    /**
     * function that creat new vector and normalized it
     * @return
     */
    public Vector normalized() {
        Vector newVec = new Vector(_head);//create new vector
        newVec.normalize();//using the previous function
        return newVec;
    }

}
