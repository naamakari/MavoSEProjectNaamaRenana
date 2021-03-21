package primitives;

import java.util.Objects;

/**
 * class that represent all the point that are at the side of the point that received
 */
public class Ray {
    final Point3D _p0;
    final Vector _dir;

    //constructor
    public Ray(Point3D p0, Vector dir) {
        _dir = dir.normalize();//normalize the direction vector
        _p0 = p0;
    }

    /**
     * getter for origin of the Ray
     * @return p0
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * getter for direction vector of the Ray
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
}
