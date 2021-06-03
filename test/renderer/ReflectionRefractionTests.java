/**
 *
 */
package renderer;


import com.sun.source.tree.UsesTree;
import elements.*;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    private Scene scene = new Scene("Test scene");

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(150, 150).setDistance(1000);

        scene._geometries.add( //
                new Sphere(new Point3D(0, 0, -50), 50) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(new Point3D(0, 0, -50), 25) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(100)));
        scene._lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004).setKq(0.0000006));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setCamera(camera) //
                .setRayTracerBase(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(2500, 2500).setDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene._geometries.add( //
                new Sphere(new Point3D(-950, -900, -1000), 400) //
                        .setEmission(new Color(0, 0, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setnShininess(20).setkT(0.5)),
                new Sphere(new Point3D(-950, -900, -1000), 200) //
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setnShininess(20)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkR(1)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkR(0.5)));

        scene._lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setKl(0.00001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracerBase(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200).setDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene._geometries.add( //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(60)), //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(60)), //
                new Sphere(new Point3D(60, 50, -50), 30) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setnShininess(30).setkT(0.6)));

        scene._lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracerBase(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void ourImage() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(1, 0, 0)) //
                .setViewPlaneSize(200, 200).setDistance(1000);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        scene._geometries.add( //
                new Plane(new Point3D(0, 0, 0), new Point3D(10, 0, 0), new Point3D(0, 10, 0))
                        .setMaterial(new Material().setkT(0).setkR(0).setKd(0.5).setKs(0.5).setnShininess(80)).setEmission(new Color(0, 0, 50)),
                new Plane(new Point3D(0, -20, 20), new Point3D(0, 20, 20), new Point3D(20, 0, -260))
                        .setMaterial(new Material().setkT(0).setkR(0).setKd(0.1).setKs(0.1).setnShininess(5)).setEmission(new Color(java.awt.Color.GRAY)),
//                new Sphere(new Point3D(40, -40, 50), 23).setEmission(new Color(200, 200, 200))
//                        .setMaterial(new Material().setkT(0.8).setkR(0).setKd(0.3).setKs(0.3).setnShininess(30)),
                new Triangle(new Point3D(30, 20, 30), new Point3D(-5, 35, 30), new Point3D(-5, 5, 30))
                        .setEmission(new Color(85,122,4)).setMaterial(new Material().setkT(0).setnShininess(30).setkR(0.1)),
                new Triangle(new Point3D(25, -20, 70), new Point3D(-10, -5, 70), new Point3D(-10, -35, 70))
                        .setEmission(new Color(85,122,4)).setMaterial(new Material().setkT(0).setnShininess(30).setkR(0.1)),
                new Triangle(new Point3D(30, -40, 200), new Point3D(-25, -20, 200), new Point3D(-25, -75, 200))
                        .setEmission(new Color(85,122,4)).setMaterial(new Material().setkT(0).setnShininess(30).setkR(0.1)));
//                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
//                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(60)), //
//                new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
//                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(60)), //
//                new Sphere(new Point3D(60, 50, -50), 30) //
//                        .setEmission(new Color(java.awt.Color.BLUE)) //
//                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setnShininess(30).setkT(0.6)));
//        scene._lights.add(new SpotLight(new Color(255, 255, 255), new Point3D(100, 50, 25), new Vector(0, 0, -1)) //
//                .setKl(4E-5).setKq(2E-7));
        scene._lights.add(new PointLight(new Color(255, 255, 255), new Point3D(40, -40, 50)));
        ImageWriter imageWriter = new ImageWriter("ourImage", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracerBase(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void MP1(){
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(1, 0, 0)) //
                .setViewPlaneSize(200, 200).setDistance(1000);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        scene._geometries.add( //
                new Triangle(new Point3D(-20, -300, 0), new Point3D(50, 50, 0), new Point3D(-200, 0, 100)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(60)).setEmission(new Color(30,187,49)), //
                new Triangle(new Point3D(50, 50, 0), new Point3D(-200, 0, 100), new Point3D(-135, 250, 0)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(60)).setEmission(new Color(30,187,49)),
                new Triangle(new Point3D(57, 48, 0), new Point3D(32, -37, 0), new Point3D(50, 50, 0)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(60)).setEmission(new Color(30,187,49)),
                new Triangle(new Point3D(57, 48, 0), new Point3D(32, -37, 0), new Point3D(40, -40, 0)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(60)).setEmission(new Color(30,187,49)));

       // scene._lights.add(new SpotLight(new Color(255, 255, 255), new Point3D(-150, -150, 1000),new Vector(1,1,-1)));
        ImageWriter imageWriter = new ImageWriter("MP1", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracerBase(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }

}
