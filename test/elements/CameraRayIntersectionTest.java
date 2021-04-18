package elements;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

public class CameraRayIntersectionTest {

    /**
     * help function for find intersections for every geometry of the view plane
     * @param geo the geometry we want to check with
     * @param cam the camera
     * @return the numbers of intersections
     */
    public int countIntersectionsHelpFunc(Geometry geo, Camera cam) {
        cam.setViewPlaneSize(3,3);
        int counter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                var Intersections = geo.findIntersections(cam.constructRayThroughPixel(3, 3, j, i));
                if (Intersections != null) {
                    counter += Intersections.size();
                }

            }
        }
        return counter;
    }

    /**
     * test for sphere
     */
    @Test
    public void CameraRaySphereIntegrationTest() {
        Sphere sphere;
        Camera camera;


        //T1: Two intersection points
        sphere = new Sphere(new Point3D(0, 0, -3), 1d);
        camera=new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
        camera.setDistance(1d);
        assertEquals(2,countIntersectionsHelpFunc(sphere,camera),"Two intersection points");

        //T2:18 intersection points
        sphere=new Sphere(new Point3D(0,0,-2.5),2.5d);
        camera=new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0));
        camera.setDistance(1d);
        assertEquals(18,countIntersectionsHelpFunc(sphere,camera),"18 intersection points");

        //T3:10 intersection points
        sphere=new Sphere(new Point3D(0,0,-2),2d);
        camera=new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0));
        camera.setDistance(1d);
        assertEquals(10,countIntersectionsHelpFunc(sphere,camera),"10 intersection points");

        //T4: 9 intersection points
        sphere=new Sphere(new Point3D(0,0,-2),4d);
        camera=new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
        camera.setDistance(1d);
        assertEquals(9,countIntersectionsHelpFunc(sphere,camera),"9 intersection points");

        //T5: Zero intersection points
        sphere=new Sphere(new Point3D(0,0,1),0.5d);
        camera=new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0));
        camera.setDistance(1d);
        assertEquals(0,countIntersectionsHelpFunc(sphere,camera),"0 intersection points");

    }

    /**
     * test for plane
     */
    @Test
    public void CameraRayPlaneIntegrationTest() {
        Plane plane;
        Camera camera;

        //T1: 9 intersection points
        plane=new Plane(new Point3D(0,0,1),new Point3D(1,0,1),new Point3D(0,1,1));
        camera=new Camera(new Point3D(0,0,1.5),new Vector(0,0,-1),new Vector(0,1,0));
        camera.setDistance(1d);
        assertEquals(9,countIntersectionsHelpFunc(plane,camera),"9 intersection points");

        //T2:9 intersection points
        plane=new Plane(new Point3D(0,0,1),new Point3D(1,0,1.5),new Point3D(0,1,1));
        camera=new Camera(new Point3D(0,0,2),new Vector(0,0,-1),new Vector(0,1,0));
        camera.setDistance(1d);
        assertEquals(9,countIntersectionsHelpFunc(plane,camera),"9 intersection points");

        //T3:6 intersection points
        plane=new Plane(new Point3D(0,0,1),new Point3D(1,0,3),new Point3D(0,1,1));
        camera=new Camera(new Point3D(0,0,2),new Vector(0,0,-1),new Vector(0,1,0));
        camera.setDistance(1d);
        assertEquals(6,countIntersectionsHelpFunc(plane,camera),"6 intersection points");

    }

    /**
     * test for triangle
     */
    @Test
    public void CameraRayTriangleIntegrationTest(){
        Triangle triangle;
        Camera camera;

        //T1:One intersection point
        triangle=new Triangle(new Point3D(0,1,-2),new Point3D(1,-1,-2),new Point3D(-1,-1,-2));
        camera=new Camera(new Point3D(0,0,2),new Vector(0,0,-1),new Vector(0,1,0));
        camera.setDistance(1d);
        assertEquals(1,countIntersectionsHelpFunc(triangle,camera),"1 intersection points");

        //T2:Two intersection point
        triangle=new Triangle(new Point3D(0,20,-2),new Point3D(1,-1,-2),new Point3D(-1,-1,-2));
        camera=new Camera(new Point3D(0,0,2),new Vector(0,0,-1),new Vector(0,1,0));
        camera.setDistance(1d);
        assertEquals(2,countIntersectionsHelpFunc(triangle,camera),"2 intersection points");

    }

}
