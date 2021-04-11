package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple test for sphere
        Sphere sphere=new Sphere(new Point3D(0,0,0),5d);
        Vector vn=new Vector(1,1,1).normalize();
        assertEquals(vn,sphere.getNormal(new Point3D(1,1,1)),"wrong normal");
    }
}