package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple test for triangle
        Point3D p1 = new Point3D(0, 0, 1);
        Point3D p2 = new Point3D(1, 0, 0);
        Point3D p3 = new Point3D(0, 1, 0);
        Triangle triangle = new Triangle(p1, p2, p3);
        Vector vn = triangle.getNormal(p1);

        boolean plusVector = new Vector(1, 1, 1).normalize().equals(vn);
        boolean minusVector = new Vector(-1, -1, -1).normalize().equals(vn);

//checks if the vector is one or opposite and if is' it is OK
        assertTrue(plusVector || minusVector, "wrong normal");


    }

    @Test
    void testFindIntersections() {
        Triangle triangle = new Triangle(
                new Point3D(1, 0, 0),
                new Point3D(0, 1, 0),
                new Point3D(0, 0, 1));
        Plane plane = new Plane(
                new Point3D(1, 0, 0),
                new Point3D(0, 1, 0),
                new Point3D(0, 0, 1));
        // ============ Equivalence Partitions Tests ==============
        Ray ray;
        // TC01: Point is inside the triangle
        ray = new Ray(new Point3D(1, 1, 1), new Vector(-1, -1, -1));
        assertEquals(new Point3D(1d / 3, 1d / 3, 1d / 3), triangle.findIntersections(ray).get(0), "Point is not inside the triangle");

        // TC02: Point is outside against edge
        ray = new Ray(new Point3D(0, 0, -1), new Vector(1, 1, 0));
        assertEquals(new Point3D(1, 1, -1), plane.findIntersections(ray).get(0), "not intersection at plane");
        assertNull(triangle.findIntersections(ray), "Point is outside against edge");

        // TC03: Point is outside against vertex
        ray = new Ray(new Point3D(0, 0, 2), new Vector(-1, -1, 0));
        assertEquals(new Point3D(-0.5, -0.5, 2), plane.findIntersections(ray).get(0), "not intersection at plane");
        assertNull(triangle.findIntersections(ray), "Point is outside against edge");


        // =============== Boundary Values Tests ==================

        // **** Group: the ray begins "before" the plane
        // TC11: Point is on edge
        ray = new Ray(new Point3D(2, 0, 0), new Vector(-2, 0.5, 0.5));
        assertEquals(new Point3D(0, 0.5, 0.5), plane.findIntersections(ray).get(0), "not intersected the plane");
        assertNull(triangle.findIntersections(ray), "point is on edge");

        // TC12: Point is on vertex
        ray = new Ray(new Point3D(2, 0, 0), new Vector(-1, 0, 0));
        assertEquals(new Point3D(1, 0, 0), plane.findIntersections(ray).get(0), "point are not at the plane");
        assertNull(triangle.findIntersections(ray), "point on the vertex");

        // TC13: Point is on edge's continuation
        ray = new Ray(new Point3D(2, 0, 0), new Vector(-2, -0.5, 1.5));
        assertEquals(new Point3D(0, -0.5, 1.5), plane.findIntersections(ray).get(0), "not intersected the plane");
        assertNull(triangle.findIntersections(ray), "Point is on edge's continuation");

    }
}