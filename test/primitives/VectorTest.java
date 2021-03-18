package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTest {
    Vector v = new Vector(1, 2, 3);

    @Test
void testVectorZero(){
    assertThrows(IllegalArgumentException.class,()->new Vector(0,0,0),"cannot be vector (0,0,0)");

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

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals( v1.length() * v2.length(),
                vr.length(),
                0.00001,
                "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)),"crossProduct() result is not orthogonal to 1st operand");
        assertTrue( isZero(vr.dotProduct(v2)),"crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class,
                () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception"  );
        // try {
        //     v1.crossProduct(v2);
        //     fail("crossProduct() for parallel vectors does not throw an exception");
        // } catch (Exception e) {}
    }



    @Test
    void testDotProduct() {
    }

    @Test
    void testAdd() {
    }

    @Test
    void testSubtract() {
    }

    @Test
    void testScale() {
    }

    @Test
    void testLengthSquared() {
    }

    @Test
    void testLength() {
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        assertTrue(isZero(vCopyNormalize.length() - 1),"ERROR: normalize() result is not a unit vector");

    }

    @Test
    void testNormalize() {
        // test vector normalization vs vector length and cross-product
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        assertEquals(vCopy, vCopyNormalize,"ERROR: normalize() function creates a new vector");



    }

    @Test
    void testNormalized() {
        Vector u = v.normalized();
        assertEquals (u ,v,"ERROR: normalizated() function does not create a new vector");
    }
}