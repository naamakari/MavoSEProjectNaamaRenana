package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple test for sphere
        Sphere sphere = new Sphere(new Point3D(0, 0, 0), 5d);
        Vector vn = new Vector(1, 1, 1).normalize();
        assertEquals(vn, sphere.getNormal(new Point3D(1, 1, 1)), "wrong normal");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere1 = new Sphere(new Point3D(1, 0, 0), 1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere1.findIntersections(new Ray(new Point3D(-1, 0, 0),
                        new Vector(1, 1, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> result = sphere1.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        assertEquals(List.of(p2),
                sphere1.findIntersections(new Ray(new Point3D(0.5, 0.5, 0),
                                new Vector(3, 1, 0))),
                "Ray starts inside the sphere");
        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere1.findIntersections(new Ray(new Point3D(2, 1, 0), new Vector(3, 1, 0))),
                "Ray starts after the sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        assertEquals(List.of(new Point3D(2, 0, 0)),
                sphere1.findIntersections(new Ray(new Point3D(1, -1, 0), new Vector(1, 1, 0))),
                "Ray starts at sphere and goes inside");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere1.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 1, 0))),
                "Ray starts at sphere and goes outside");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        Vector vec2 = new Vector(1, 0, 0);
        Point3D poi3 = new Point3D(4, 0, 0);
        Vector vec3 = new Vector(-1, 0, 0);
        Point3D poi4 = new Point3D(2, 0, 0);
        Point3D poi5 = new Point3D(0, 0, 0);
        List<Point3D> myList = List.of(poi4, poi5);
        assertEquals(myList, sphere1.findIntersections(new Ray(poi3, vec3)), "Ray starts before the sphere");

        // TC14: Ray starts at sphere and goes inside (1 points)
        assertEquals(poi4, sphere1.findIntersections(new Ray(new Point3D(0, 0, 0), vec2)).get(0), "Ray starts at sphere and goes inside");

        // TC15: Ray starts inside (1 points)
        Ray ray3 = new Ray(new Point3D(1.5, 0, 0), vec2);
        assertEquals(poi4, sphere1.findIntersections(ray3).get(0), "Ray starts inside");

        // TC16: Ray starts at the center (1 points)
        Point3D poi1 = new Point3D(1, 0, 0);
        Vector vec1 = new Vector(3, 2, 0);
        Ray ray2 = new Ray(poi1, vec1);
        assertEquals(poi1.add(vec1.scale(1d)), sphere1.findIntersections(ray2).get(0), "the ray starts at the center of the sphere");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere1.findIntersections(new Ray(poi4, vec2)), "Ray starts at sphere and goes outside");

        // TC18: Ray starts after sphere (0 points)
        Point3D poi2 = new Point3D(4, 0, 0);
        assertNull(sphere1.findIntersections(new Ray(poi2, vec2)), "ray starts after sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
       assertNull(sphere1.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(1, 0, 0))),
                "Ray starts before the tangent point");

        // TC20: Ray starts at the tangent point
        assertNull(sphere1.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(1, 0, 0))),
                "Ray starts at the tangent point");

        // TC21: Ray starts after the tangent point
        assertNull(sphere1.findIntersections(new Ray(new Point3D(2, 1, 0), new Vector(1, 0, 0))),
                "Ray starts after the tangent point");

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        Ray ray4 = new Ray(poi4, new Vector(0, 0, 1));
        assertNull(sphere1.findIntersections(ray4), " Ray's line is outside, ray is orthogonal to ray start to sphere's center line");

    }


}