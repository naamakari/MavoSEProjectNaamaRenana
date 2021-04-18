package geometries;

import org.junit.jupiter.api.Test;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    Triangle triangle = new Triangle(new Point3D(0, 1.5, 0), new Point3D(0, 0.5, 0), new Point3D(0, 1, 1));
    Sphere sphere = new Sphere(new Point3D(0, 0, 1), 1d);
    Plane plane = new Plane(new Point3D(0, 1, 1), new Vector(0, -1, 1));
    Ray ray;
    Geometries geo;

    @Test
    void testFindIntersections() {
        geo = new Geometries();
        // ============ Equivalence Partitions Tests ==============
//T01:Some shapes (but not all) are cut
        geo = new Geometries(triangle, sphere, plane);
        ray = new Ray(new Point3D(0, -1, 0), new Vector(-1, 2, 1));
        assertEquals(3, geo.findIntersections(ray).size(), "Some shapes (but not all) are cut");


        // =============== Boundary Values Tests ==================

        //T11: empty collection
        ray = new Ray(new Point3D(1, 0, 1), new Vector(1, 0, 0));
        assertNull(geo.findIntersections(ray), "empty collection");

        //T12: No shape is cut
        ray = new Ray(new Point3D(2, 0, 0), new Vector(0, -1, 0));
        assertNull(geo.findIntersections(ray), "No shape is cut");

        //T13: Only one shape is cut
        ray = new Ray(new Point3D(0, 0, 2.5), new Vector(0, 1, 0));
        assertEquals(1, geo.findIntersections(ray).size(), "Only one shape is cut");

        //T14:All shapes are cut
         ray=new Ray(new Point3D(1.45,1.67,0),new Vector(-1.45,-0.74,0.5));
        assertEquals(4, geo.findIntersections(ray).size(), "All shapes are cut");


    }
}