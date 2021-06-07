package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * class for the camera that take picture of the scene
 */
public class Camera {
    final Point3D _p0;
    final Vector _vTo;
    final Vector _vUp;
    final Vector _vRight;
    private double _distance;
    private double _width;
    private double _height;

    /**
     * constructor
     *
     * @param p0  start point
     * @param vTo z axis
     * @param vUp y axis
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        _p0 = p0;
        _vTo = vTo.normalized();
        _vUp = vUp.normalized();
        if (!isZero(_vTo.dotProduct(_vUp))) {
            throw new IllegalArgumentException("vto and vup not orthogonal");
        }
        _vRight = _vTo.crossProduct(_vUp);
    }

    /**
     * like builder pattern
     *
     * @param width  of the view plane
     * @param height of the view plane
     * @return
     */
    public Camera setViewPlaneSize(double width, double height) {
        _width = width;
        _height = height;
        return this;
    }

    public Camera setDistance(double distance) {
        _distance = distance;
        return this;
    }

    /**
     * A function that builds a ray through a pixel-particle, the function is summed inside
     * a nested loop that goes into all the pixel-particles according to the number of samples we determined
     *
     * @param nX              width of the row, amount of columns
     * @param nY              width of the column, amount of rows
     * @param j               y axis
     * @param i               x axis
     * @param numberOfSamples the number of the samples we determined
     * @param ii              the position within the pixel
     * @param jj              the position within the pixel
     * @return
     */
    public Ray constructRayThroughRandomPixel(int nX, int nY, int j, int i, int numberOfSamples, int ii, int jj) {
        double x;
        double y;
        x = (i + (ii + Math.random()) / numberOfSamples) / nX;
        y = (j + (jj + Math.random()) / numberOfSamples) / nY;
        Point3D pXY = new Point3D(x, y, 0d);
        Ray ray = new Ray(_p0, pXY.subtract(_p0));
        return ray;
    }


    /**
     * function for finding the ray that pass at the center of the pixel
     *
     * @param nX width of the row, amount of columns
     * @param nY width of the column, amount of rows
     * @param j  y axis
     * @param i  x axis
     * @return
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {

        Point3D Pc = _p0.add(_vTo.scale(_distance));//image center

        /**
         * Ratio (pixel width & height)
         */

        double Ry = _height / nY;
        double Rx = _width / nX;

        /**
         * Pixel[i,j] center
         */

        double Yi = -(i - (nY - 1) / 2d) * Ry;
        double Xj = (j - (nX - 1) / 2d) * Rx;

        Point3D Pij = Pc;
        /**
         *checks numbers does not make zero vector
         */

        if (!isZero(Xj)) {
            Pij = Pij.add(_vRight.scale(Xj));
        }

        if (!isZero(Yi)) {
            Pij = Pij.add(_vUp.scale(Yi));
        }

        return new Ray(_p0, Pij.subtract(_p0).normalize());
    }

    public Point3D getP0() {
        return _p0;
    }

    public Vector getvTo() {
        return _vTo;
    }

    public Vector getvUp() {
        return _vUp;
    }

    public Vector getvRight() {
        return _vRight;
    }
}
