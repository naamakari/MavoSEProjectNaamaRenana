package primitives;

import geometries.Geometries;
import geometries.Intersectable.*;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    /**
     * test to check the fonction of find the closest point to a ray
     */
    @Test
    void testFindClosestPoint() {
        Ray ray = new Ray(new Point3D(0, -1, 0), new Vector(0, 1, 1));
        List<Point3D> listPoints1 = new LinkedList<>();
        Point3D p1 = new Point3D(0, 1, 2);
        Point3D p2 = new Point3D(0, 0, 1);
        Point3D p3 = new Point3D(0, 2, 3);
        listPoints1.add(p1);
        listPoints1.add(p2);
        listPoints1.add(p3);
        // ============ Equivalence Partitions Tests ==============
        //T01:A point in the middle of the list is closest to the beginning of the ray
        assertEquals(p2, ray.findClosestPoint(listPoints1), "A point in the middle of the list is closest to the beginning of the ray");

        // =============== Boundary Values Tests ==================

        //T11: empty list
        listPoints1.clear();
        assertNull(ray.findClosestPoint(listPoints1), "empty list");

        //T12: The first point is closest to the beginning of the ray
        listPoints1.add(p2);
        listPoints1.add(p1);
        listPoints1.add(p3);
        assertEquals(p2, ray.findClosestPoint(listPoints1), "The first point is closest to the beginning of the ray");

        //T13: The last point is closest to the beginning of the ray
        listPoints1.clear();
        listPoints1.add(p3);
        listPoints1.add(p1);
        listPoints1.add(p2);
        assertEquals(p2, ray.findClosestPoint(listPoints1), "The last point is closest to the beginning of the ray");

    }

    @Test
    void testGetClosestGeoPoint() {
        Ray ray = new Ray(new Point3D(0, 0, 0), new Vector(1, 1, 1));
        List<GeoPoint> listPoints1 = new LinkedList<>();
        GeoPoint gP1 = new GeoPoint(new Sphere(new Point3D(2,2,3),2d),new Point3D(3.39, 3.39, 3.39));
        GeoPoint gP2 = new GeoPoint(new Sphere(new Point3D(2,2,3),2d),new Point3D(1.3, 1.3, 1.3));
        GeoPoint gP3 = new GeoPoint(new Triangle(new Point3D(2,2,3),new Point3D(3,0,0),new Point3D(0,3,0) ),new Point3D(1.8, 1.8, 1.8));
        listPoints1.add(gP1);
        listPoints1.add(gP2);
        listPoints1.add(gP3);

        // ============ Equivalence Partitions Tests ==============
        //T01:A point in the middle of the list is closest to the beginning of the ray
        assertEquals(gP2, ray.getClosestGeoPoint(listPoints1), "A point in the middle of the list is closest to the beginning of the ray");

        // =============== Boundary Values Tests ==================

        //T11: empty list
        listPoints1.clear();
        assertNull(ray.getClosestGeoPoint(listPoints1), "empty list");

        //T12: The first point is closest to the beginning of the ray
        listPoints1.add(gP2);
        listPoints1.add(gP1);
        listPoints1.add(gP3);
        assertEquals(gP2, ray.getClosestGeoPoint(listPoints1), "The first point is closest to the beginning of the ray");

        //T13: The last point is closest to the beginning of the ray
        listPoints1.clear();
        listPoints1.add(gP3);
        listPoints1.add(gP1);
        listPoints1.add(gP2);

        assertEquals(gP2, ray.getClosestGeoPoint(listPoints1), "The last point is closest to the beginning of the ray");

    }
}