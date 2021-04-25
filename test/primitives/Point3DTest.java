package primitives;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {
    Point3D p1 = new Point3D(0, 0, 0);
    Point3D p2 = new Point3D(1, 2, 3);
    Point3D p3 =new Point3D(1,2,2);
    Point3D p4 =new Point3D(-1,-2,-2);
    Vector v1 = new Vector(1, 2, 3);

    /**
     * test that checks if substract is doing well
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(v1, p2.subtract(p1), "The point must be himself");
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> p2.subtract(p2), "point (0,0,0) cannot be in our program");

    }

    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(p2, p1.add(v1), "The point must be himself");
    }

    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(p1.distanceSquared(p2), 14, "the square distance must be this value");
    }

    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(p1.distance(p3), 3, "the distance must be this value");
    }

    /**
     * test that checks the function that checks if points are on the same line
     */
    @Test
    void testisSameLine(){
        Point3D p1=new Point3D(1,2,3);
        Point3D p2=new Point3D(2,4,6);
        assertTrue(p1.isSameLine(p2),"points are on the same line");
    }
}