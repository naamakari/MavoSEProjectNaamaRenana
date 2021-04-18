package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTest {
    Vector v = new Vector(1, 2, 3);
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    @Test
    void testVectorZero() {
        //check Zero vector non creation
        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0), "cannot be vector (0,0,0)");
        out.println("eize yofi");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(v1.length() * v2.length(),
                vr.length(),
                0.00001,
                "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class,
                () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");
        // try {
        //     v1.crossProduct(v2);
        //     fail("crossProduct() for parallel vectors does not throw an exception");
        // } catch (Exception e) {}

    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void testDotProduct() {
        //TC0:check if the vectors are orthogonal
        assertTrue(isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");

        assertTrue(isZero(v1.dotProduct(v2) + 28), "ERROR: dotProduct() wrong value");
    }

    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============

        // Test operations with points and vectors
        Point3D p1 = new Point3D(1, 2, 3);
        assertEquals(Point3D.ZERO, (p1.add(new Vector(-1, -2, -3))), "ERROR: Point + Vector does not work correctly");

    }

    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        Point3D p1 = new Point3D(1, 2, 3);
        assertEquals(new Vector(1, 1, 1), (new Point3D(2, 3, 4).subtract(p1)), "ERROR: Point - Point does not work correctly");

        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1),
                "ERROR: subtract throw an exception");

    }

    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============

        assertEquals(new Vector(2, 4, 6), new Vector(1, 2, 3).scale(2), "ERROR: scale function does not work correctly");
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> v.scale(0), "cannot scale at 0 because its vector 0");
    }

    @Test
    void testLengthSquared() {
        assertTrue(isZero(v1.lengthSquared() - 14), "ERROR: lengthSquared() wrong value");
    }

    @Test
    void testLength() {

        assertTrue(isZero(new Vector(0, 3, 4).length() - 5), "ERROR: length() wrong value");

        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        assertTrue(isZero(vCopyNormalize.length() - 1), "ERROR: normalize() result is not a unit vector");
    }

    @Test
    void testNormalize() {
        // test vector normalization vs vector length and cross-product
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        assertEquals(vCopy, vCopyNormalize, "ERROR: normalize() function creates a new vector");
    }

    //the test is not working well and i dont know why, it is define ok as i know.
    //does it true now?
    @Test
    void testNormalized() {
        Vector u = v.normalized();
        assertTrue(u != v, "ERROR: normalizated() function does not create a new vector");
    }
}