package primitives;

import geometries.Geometries;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

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
}