package geometries;

import java.util.List;

import primitives.*;

import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */

    protected List<Point3D> vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected final Plane plane;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point3D... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3) {
            //create the box for triangle
            //the min and max x,y,z
            double minX = Double.POSITIVE_INFINITY;
            double minY = Double.POSITIVE_INFINITY;
            double minZ = Double.POSITIVE_INFINITY;
            double maxX = Double.NEGATIVE_INFINITY;
            double maxY = Double.NEGATIVE_INFINITY;
            double maxZ = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < vertices.length; i++) {
                if (vertices[i].getX() < minX) {
                    minX = vertices[i].getX();
                }
                if (vertices[i].getY() < minY) {
                    minY = vertices[i].getY();
                }
                if (vertices[i].getZ() < minZ) {
                    minZ = vertices[i].getZ();
                }
                if (vertices[i].getX() > maxX) {
                    maxX = vertices[i].getX();
                }
                if (vertices[i].getY() > maxY) {
                    maxY = vertices[i].getY();
                }
                if (vertices[i].getZ() > maxZ) {
                    maxZ = vertices[i].getZ();
                }
            }
            _box.setUpRightBehind(new Point3D(maxX, maxY, maxZ));
            _box.setDownLeftFront(new Point3D(minX, minY, minZ));
            //the center of the box
            double centerX = maxX - minX / 2;
            double centerY = maxY - minY / 2;
            double centerZ = maxZ - minZ / 2;
            _box.setCenter(new Point3D(centerX, centerY, centerZ));
            return; // no need for more tests for a Triangle
        }

//        Vector n = plane.getNormal(null);
        Vector n = plane.getNormal();

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }

        //create the box for polygon
        //the min and max x,y,z
        double minX=Double.POSITIVE_INFINITY;
        double minY=Double.POSITIVE_INFINITY;
        double minZ=Double.POSITIVE_INFINITY;
        double maxX=Double.NEGATIVE_INFINITY;
        double maxY=Double.NEGATIVE_INFINITY;
        double maxZ=Double.NEGATIVE_INFINITY;
        for (int i = 0; i < vertices.length; i++) {
            if(vertices[i].getX()<minX){
                minX=vertices[i].getX();
            }
            if(vertices[i].getY()<minY){
                minY=vertices[i].getY();
            }
            if(vertices[i].getZ()<minZ){
                minZ=vertices[i].getZ();
            }
            if(vertices[i].getX()>maxX){
                maxX=vertices[i].getX();
            }
            if(vertices[i].getY()>maxY){
                maxY=vertices[i].getY();
            }
            if(vertices[i].getZ()>maxZ){
                maxZ=vertices[i].getZ();
            }
        }
        _box.setUpRightBehind(new Point3D(maxX,maxY,maxZ));
        _box.setDownLeftFront(new Point3D(minX,minY,minZ));
        //the center of the box
        double centerX=maxX-minX/2;
        double centerY=maxY-minY/2;
        double centerZ=maxZ-minZ/2;
        _box.setCenter(new Point3D(centerX,centerY,centerZ));
    }


    @Override
    public Box getBox() {
        return _box;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return plane.getNormal(null);
    }


    /**
     * implements the method of find Geo intersections for polygon
     * @param ray the specific ray we check the intersection with
     * @param maxDistance the distance from the light
     * @return list of the goe points intersection with the ray
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray,double maxDistance) {

        //find the intersection withe the plane
        List<GeoPoint> result = plane.findGeoIntersections(ray,maxDistance);

        //if there is no intersection points with the plane
        if (result == null) {
            return result;
        }

        Point3D P0 = ray.getP0();
        Vector v = ray.getDir();

        Point3D P1 = vertices.get(1);
        Point3D P2 = vertices.get(0);

        Vector v1 = P1.subtract(P0);
        Vector v2 = P2.subtract(P0);

        double sign = alignZero(v.dotProduct(v1.crossProduct(v2)));

        if (isZero(sign)) {
            return null;
        }

        boolean positive = sign > 0;

        //iterate through all vertices of the polygon
        for (int i = vertices.size() - 1; i > 0; --i) {
            v1 = v2;
            v2 = vertices.get(i).subtract(P0);

            sign = alignZero(v.dotProduct(v1.crossProduct(v2)));
            if (isZero(sign)) {
                return null;
            }

            if (positive != (sign > 0)) {
                return null;
            }
        }
        return List.of(new GeoPoint(this,result.get(0)._point));
    }
}
