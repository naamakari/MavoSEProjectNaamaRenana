package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
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
}