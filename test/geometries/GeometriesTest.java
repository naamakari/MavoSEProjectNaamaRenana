package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    Triangle triangle = new Triangle(new Point3D(0, 1.5, 0), new Point3D(0, 0.5, 0), new Point3D(0, 1, 1));
    Sphere sphere = new Sphere(new Point3D(0, 0, 1), 1d);
    Ray ray;
    Geometries geo;
    @Test
    void testFindIntersections() {
        geo = new Geometries();
        //T1: empty collection
        ray = new Ray(new Point3D(1, 0, 1), new Vector(1, 0, 0));
        assertNull(geo.findIntersections(ray), "empty collection");

        //T2: No shape is cut
        geo=new Geometries(triangle,sphere);
        ray=new Ray(new Point3D(2,0,0),new Vector(0,-1,0));
        assertNull(geo.findIntersections(ray),"No shape is cut");
    }
}