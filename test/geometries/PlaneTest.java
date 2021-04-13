package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple test for plane
        Point3D p1 = new Point3D(0, 0, 1);
        Point3D p2 = new Point3D(1, 0, 0);
        Point3D p3 = new Point3D(0, 1, 0);
        Plane plane1 = new Plane(p1, p2, p3);
        Vector vn = plane1.getNormal(p1);

        boolean plusVector = new Vector(1, 1, 1).normalize().equals(vn);
        boolean minusVector = new Vector(-1, -1, -1).normalize().equals(vn);


        assertTrue(plusVector || minusVector, "wrong normal");


    }

    @Test
    void testFindIntersections() {
        Plane plane = new Plane(new Point3D(0, 0, 1), new Point3D(0, 1, 1), new Point3D(1, 0, 1));
        Ray ray;
        // ============ Equivalence Partitions Tests ==============

        // TC01:Ray intersects the plane
        ray = new Ray(new Point3D(1, 0, 0), new Vector(-1, 0, 1));
        assertEquals(new Point3D(0, 0, 1), plane.findIntersections(ray).get(0), "Ray intersects the plane");

        // TC02: Ray does not intersect the plane
        ray = new Ray(new Point3D(0, 0, 2), new Vector(1, 0, 0));
        assertNull(plane.findIntersections(ray), "Ray does not intersect the plane");


        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane
        // TC11: ray included in the plane
        ray = new Ray(new Point3D(0, 0, 1), new Vector(1, 0, 0));
        assertNull(plane.findIntersections(ray), "ray included in the plane");

        // TC12: ray not included in the plane
        ray = new Ray(new Point3D(0, 0, 2), new Vector(1, 0, 0));
        assertNull(plane.findIntersections(ray), "ray included in the plane");

        // **** Group: Ray is orthogonal to the plane
        // TC13: according to 𝑃0 -starts before the plane
        ray = new Ray(new Point3D(0, 0, 0.5), new Vector(0, 0, 1));
        assertEquals(new Point3D(0, 0, 1), plane.findIntersections(ray).get(0), "Ray is orthogonal to the plane and starts before the plane");


        // TC14: according to 𝑃0- starts in the plane
        ray = new Ray(new Point3D(0, 0, 1), new Vector(0, 0, 1));
        assertNull(plane.findIntersections(ray), "Ray is orthogonal to the plane and starts in the plane");


        // TC15: according to 𝑃0 -starts after the plane
        ray = new Ray(new Point3D(0, 0, 2), new Vector(0, 0, 1));
        assertNull(plane.findIntersections(ray), "Ray is orthogonal to the plane and starts after the plane");

        // TC19: Ray is neither orthogonal nor parallel to and begins at the plane (p0 is in the plane, but not the ray)
        ray=new Ray(new Point3D(1,1,1),new Vector(-1,-1,1));
        assertNull(plane.findIntersections(ray),"Ray is neither orthogonal nor parallel to and begins at the plane (p0 is in the plane, but not the ray)");

        // TC20:  Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane (q)
        ray=new Ray(new Point3D(0,1,1),new Vector(0,-1,1));
        assertNull(plane.findIntersections(ray)," Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane (q)");

    }
}