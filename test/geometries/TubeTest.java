package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // =============== Boundary Values Tests ==================
        // TC01: There is a test here that includes the border case
        Tube tube = new Tube(new Ray(new Point3D(0, 0, 1), new Vector(0, -1, 0)), 1.0);
        Vector nor = tube.getNormal(new Point3D(0, 0.5, 2)).normalize();
        assertEquals(0d, nor.dotProduct(tube.getAxisRay().getDir()), "wrong normal to the tube");
    }
}