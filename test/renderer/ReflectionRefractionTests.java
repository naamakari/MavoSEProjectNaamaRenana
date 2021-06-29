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

import java.util.Random;

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
                        .setEmission(new Color(85, 122, 4)).setMaterial(new Material().setkT(0).setnShininess(30).setkR(0.1)),
                new Triangle(new Point3D(25, -20, 70), new Point3D(-10, -5, 70), new Point3D(-10, -35, 70))
                        .setEmission(new Color(85, 122, 4)).setMaterial(new Material().setkT(0).setnShininess(30).setkR(0.1)),
                new Triangle(new Point3D(30, -40, 200), new Point3D(-25, -20, 200), new Point3D(-25, -75, 200))
                        .setEmission(new Color(85, 122, 4)).setMaterial(new Material().setkT(0).setnShininess(30).setkR(0.1)));
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
        render.renderImage(10);
        render.writeToImage();
    }

    @Test
    public void BVH() {
        Point3D L = new Point3D(-5, -60, 50);
        Point3D M = new Point3D(-52, -60, 50);
        Point3D N = new Point3D(-27, -66, 80);
        Point3D O = new Point3D(-30, -54, 20);
        Point3D P = new Point3D(-27, -37, 80);
        Point3D Q = new Point3D(-52, -31, 50);
        Point3D R = new Point3D(-30, -25, 20);
        Point3D S = new Point3D(-5, -31, 50);
        Random rand = new Random();


        Material material=new Material().setnShininess(500).setkT(0.3).setKs(0.3).setKd(0.5);
        Color color1=new Color(224,233,236);
        Color color2=new Color(169,169,170);
        Color color3=new Color(198,203,204);
        Color color4=new Color(169,169,170);
        Color color5=new Color(194,204,193);
        Color color6=new Color(169,169,170);
        Color color7=new Color(191,200,204);
        Color color8=new Color(169,169,170);


        Point3D M8 = new Point3D(15, 0, 55);
        Point3D N8 = new Point3D(5, 0, 42.5);
        Point3D E8 = new Point3D(-5, 0, 42.5);
        Point3D F8 = new Point3D(-15, 0, 55);
        Point3D G8 = new Point3D(15, 0, 70);
        Point3D H8 = new Point3D(-15, 0, 70);
        Point3D I8 = new Point3D(5, 0, 80);
        Point3D J8 = new Point3D(-5, 0, 80);
        Point3D K8 = new Point3D(0, 10, 62.5);
        Point3D L8 = new Point3D(0, -25, 62.5);

        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(500, 500).setDistance(1000);
        scene.setAmbientLight(new AmbientLight());
        scene._geometries.add(
                //down
                new Polygon(new Point3D(120, -50, 0), new Point3D(120, -110, 100), new Point3D(-120, -110, 100), new Point3D(-120, -50, 0))
                        .setEmission(new Color(239, 241, 186)).setMaterial(new Material().setKs(0.3).setnShininess(400).setKd(0.5).setkR(0).setkT(0)));

        scene._geometries.add(new Polygon(I8, G8, M8, N8, E8, F8, H8, J8).setEmission(new Color(java.awt.Color.WHITE)),
                //UP TRIANGLES
                new Triangle(K8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(K8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(K8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(K8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, E8, F8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(K8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(K8, J8, I8).setEmission(new Color(color8)).setMaterial(material),
                //DOWN TRIANGLES
                new Triangle(L8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(L8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(L8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(L8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(L8, E8, F8).setEmission(new Color(color5)).setMaterial(material),
                new Triangle(L8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(L8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(L8, J8, I8).setEmission(new Color(color8)).setMaterial(material));

         M8 = new Point3D(35, 35, 55);
         N8 = new Point3D(25, 35, 42.5);
         E8 = new Point3D(15, 35, 42.5);
         F8 = new Point3D(5, 35, 55);
         G8 = new Point3D(35, 35, 70);
         H8 = new Point3D(5, 35, 70);
         I8 = new Point3D(25, 35, 80);
         J8 = new Point3D(15, 35, 80);
         K8 = new Point3D(20, 45, 62.5);
        L8 = new Point3D(20, 10, 62.5);

        scene._geometries.add(new Polygon(I8, G8, M8, N8, E8, F8, H8, J8).setEmission(new Color(java.awt.Color.WHITE)),
                //UP TRIANGLES
                new Triangle(K8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(K8, G8, M8).setEmission(new Color(color8)).setMaterial(material),
                new Triangle(K8, M8, N8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(K8, N8, E8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(K8, E8, F8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(K8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(K8, H8, J8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, J8, I8).setEmission(new Color(color5)).setMaterial(material),
                //DOWN TRIANGLES
                new Triangle(L8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(L8, G8, M8).setEmission(new Color(color8)).setMaterial(material),
                new Triangle(L8, M8, N8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(L8, N8, E8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(L8, E8, F8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(L8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(L8, H8, J8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(L8, J8, I8).setEmission(new Color(color5)).setMaterial(material));



        M8 = new Point3D(-5, 35, -195);
        N8 = new Point3D(-15, 35, -207.5);
        E8 = new Point3D(-25, 35, -207.5);
        F8 = new Point3D(-35, 35, -195);
        G8 = new Point3D(-5, 35, -180);
        H8 = new Point3D(-35, 35, -180);
        I8 = new Point3D(-15, 35, -170);
        J8 = new Point3D(-25, 35, -170);
        K8 = new Point3D(-20, 45, -187.5);
        L8 = new Point3D(-20, 10, -187.5);

        scene._geometries.add(new Polygon(I8, G8, M8, N8, E8, F8, H8, J8).setEmission(new Color(java.awt.Color.WHITE)),
                //UP TRIANGLES
                new Triangle(K8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(K8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(K8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(K8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, E8, F8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(K8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(K8, J8, I8).setEmission(new Color(color8)).setMaterial(material),
                //DOWN TRIANGLES
                new Triangle(L8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(L8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(L8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(L8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(L8, E8, F8).setEmission(new Color(color5)).setMaterial(material),
                new Triangle(L8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(L8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(L8, J8, I8).setEmission(new Color(color8)).setMaterial(material));

        M8 = new Point3D(20, 80, -395);
        N8 = new Point3D(10, 80, -407.5);
        E8 = new Point3D(0, 80, -407.5);
        F8 = new Point3D(-10, 80, -395);
        G8 = new Point3D(20, 80, -380);
        H8 = new Point3D(-10, 80, -380);
        I8 = new Point3D(10, 80, -370);
        J8 = new Point3D(0, 80, -370);
        K8 = new Point3D(5, 90, -387.5);
        L8 = new Point3D(5, 55, -387.5);

        scene._geometries.add(new Polygon(I8, G8, M8, N8, E8, F8, H8, J8).setEmission(new Color(java.awt.Color.WHITE)),
                //UP TRIANGLES
                new Triangle(K8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(K8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(K8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(K8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, E8, F8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(K8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(K8, J8, I8).setEmission(new Color(color8)).setMaterial(material),
                //DOWN TRIANGLES
                new Triangle(L8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(L8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(L8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(L8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(L8, E8, F8).setEmission(new Color(color5)).setMaterial(material),
                new Triangle(L8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(L8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(L8, J8, I8).setEmission(new Color(color8)).setMaterial(material));

        M8 = new Point3D(90, 60, 100);
        N8 = new Point3D(80, 60, 87.5);
        E8 = new Point3D(70, 60, 87.5);
        F8 = new Point3D(60, 60, 100);
        G8 = new Point3D(90, 60, 115);
        H8 = new Point3D(60, 60, 115);
        I8 = new Point3D(80, 60, 125);
        J8 = new Point3D(70, 60, 125);
        K8 = new Point3D(75, 70, 107.5);
        L8 = new Point3D(75, 35, 107.5);

        scene._geometries.add(new Polygon(I8, G8, M8, N8, E8, F8, H8, J8).setEmission(new Color(java.awt.Color.WHITE)),
                //UP TRIANGLES
                new Triangle(K8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(K8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(K8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(K8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, E8, F8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(K8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(K8, J8, I8).setEmission(new Color(color8)).setMaterial(material),
                //DOWN TRIANGLES
                new Triangle(L8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(L8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(L8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(L8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(L8, E8, F8).setEmission(new Color(color5)).setMaterial(material),
                new Triangle(L8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(L8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(L8, J8, I8).setEmission(new Color(color8)).setMaterial(material));

        M8 = new Point3D(55, 0, 300);
        N8 = new Point3D(45, 0, 287.5);
        E8 = new Point3D(35, 0, 287.5);
        F8 = new Point3D(25, 0, 300);
        G8 = new Point3D(55, 0, 315);
        H8 = new Point3D(25, 0, 315);
        I8 = new Point3D(45, 0, 325);
        J8 = new Point3D(35, 0, 325);
        K8 = new Point3D(40, 10, 307.5);
        L8 = new Point3D(40, -25, 307.5);

        scene._geometries.add(new Polygon(I8, G8, M8, N8, E8, F8, H8, J8).setEmission(new Color(java.awt.Color.WHITE)),
                //UP TRIANGLES
                new Triangle(K8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(K8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(K8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(K8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, E8, F8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(K8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(K8, J8, I8).setEmission(new Color(color8)).setMaterial(material),
                //DOWN TRIANGLES
                new Triangle(L8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(L8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(L8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(L8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(L8, E8, F8).setEmission(new Color(color5)).setMaterial(material),
                new Triangle(L8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(L8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(L8, J8, I8).setEmission(new Color(color8)).setMaterial(material));

        M8 = new Point3D(70, 50, -100);
        N8 = new Point3D(60, 50, -112.5);
        E8 = new Point3D(50, 50, -112.5);
        F8 = new Point3D(40, 50, -100);
        G8 = new Point3D(70, 50, -85);
        H8 = new Point3D(40, 50, -85);
        I8 = new Point3D(60, 50, -75);
        J8 = new Point3D(50, 50, -75);
        K8 = new Point3D(55, 60, -92.5);
        L8 = new Point3D(55, 25, -92.5);

        scene._geometries.add(new Polygon(I8, G8, M8, N8, E8, F8, H8, J8).setEmission(new Color(java.awt.Color.WHITE)),
                //UP TRIANGLES
                new Triangle(K8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(K8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(K8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(K8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, E8, F8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(K8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(K8, J8, I8).setEmission(new Color(color8)).setMaterial(material),
                //DOWN TRIANGLES
                new Triangle(L8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(L8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(L8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(L8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(L8, E8, F8).setEmission(new Color(color5)).setMaterial(material),
                new Triangle(L8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(L8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(L8, J8, I8).setEmission(new Color(color8)).setMaterial(material));

        M8 = new Point3D(-90, 60, 100);
        N8 = new Point3D(-80, 60, 87.5);
        E8 = new Point3D(-70, 60, 87.5);
        F8 = new Point3D(-60, 60, 100);
        G8 = new Point3D(-90, 60, 115);
        H8 = new Point3D(-60, 60, 115);
        I8 = new Point3D(-80, 60, 125);
        J8 = new Point3D(-70, 60, 125);
        K8 = new Point3D(-75, 70, 107.5);
        L8 = new Point3D(-75, 35, 107.5);

        scene._geometries.add(new Polygon(I8, G8, M8, N8, E8, F8, H8, J8).setEmission(new Color(java.awt.Color.WHITE)),
                //UP TRIANGLES
                new Triangle(K8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(K8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(K8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(K8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, E8, F8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(K8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(K8, J8, I8).setEmission(new Color(color8)).setMaterial(material),
                //DOWN TRIANGLES
                new Triangle(L8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(L8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(L8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(L8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(L8, E8, F8).setEmission(new Color(color5)).setMaterial(material),
                new Triangle(L8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(L8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(L8, J8, I8).setEmission(new Color(color8)).setMaterial(material));

        M8 = new Point3D(-55, 0, 500);
        N8 = new Point3D(-45, 0, 487.5);
        E8 = new Point3D(-35, 0, 487.5);
        F8 = new Point3D(-25, 0, 500);
        G8 = new Point3D(-55, 0, 515);
        H8 = new Point3D(-25, 0, 515);
        I8 = new Point3D(-45, 0, 525);
        J8 = new Point3D(-35, 0, 525);
        K8 = new Point3D(-40, 10, 507.5);
        L8 = new Point3D(-40, -25, 507.5);

        scene._geometries.add(new Polygon(I8, G8, M8, N8, E8, F8, H8, J8).setEmission(new Color(java.awt.Color.WHITE)),
                //UP TRIANGLES
                new Triangle(K8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(K8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(K8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(K8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, E8, F8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(K8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(K8, J8, I8).setEmission(new Color(color8)).setMaterial(material),
                //DOWN TRIANGLES
                new Triangle(L8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(L8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(L8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(L8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(L8, E8, F8).setEmission(new Color(color5)).setMaterial(material),
                new Triangle(L8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(L8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(L8, J8, I8).setEmission(new Color(color8)).setMaterial(material));

        M8 = new Point3D(-70, 50, -100);
        N8 = new Point3D(-60, 50, -112.5);
        E8 = new Point3D(-50, 50, -112.5);
        F8 = new Point3D(-40, 50, -100);
        G8 = new Point3D(-70, 50, -85);
        H8 = new Point3D(-40, 50, -85);
        I8 = new Point3D(-60, 50, -75);
        J8 = new Point3D(-50, 50, -75);
        K8 = new Point3D(-55, 60, -92.5);
        L8 = new Point3D(-55, 25, -92.5);

        scene._geometries.add(new Polygon(I8, G8, M8, N8, E8, F8, H8, J8).setEmission(new Color(java.awt.Color.WHITE)),
                //UP TRIANGLES
                new Triangle(K8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(K8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(K8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(K8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, E8, F8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(K8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(K8, J8, I8).setEmission(new Color(color8)).setMaterial(material),
                //DOWN TRIANGLES
                new Triangle(L8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(L8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(L8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(L8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(L8, E8, F8).setEmission(new Color(color5)).setMaterial(material),
                new Triangle(L8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(L8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(L8, J8, I8).setEmission(new Color(color8)).setMaterial(material));

        M8 = new Point3D(-30, -20, -395);
        N8 = new Point3D(-40, -20, -407.5);
        E8 = new Point3D(-50, -20, -407.5);
        F8 = new Point3D(-60, -20, -395);
        G8 = new Point3D(-30, -20, -380);
        H8 = new Point3D(-60, -20, -380);
        I8 = new Point3D(-40, -20, -370);
        J8 = new Point3D(-50, -20, -370);
        K8 = new Point3D(-45, -10, -387.5);
        L8 = new Point3D(-45, -45, -387.5);

        scene._geometries.add(new Polygon(I8, G8, M8, N8, E8, F8, H8, J8).setEmission(new Color(java.awt.Color.WHITE)),
                //UP TRIANGLES
                new Triangle(K8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(K8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(K8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(K8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, E8, F8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(K8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(K8, J8, I8).setEmission(new Color(color8)).setMaterial(material),
                //DOWN TRIANGLES
                new Triangle(L8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(L8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(L8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(L8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(L8, E8, F8).setEmission(new Color(color5)).setMaterial(material),
                new Triangle(L8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(L8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(L8, J8, I8).setEmission(new Color(color8)).setMaterial(material));

        M8 = new Point3D(30, 100, -395);
        N8 = new Point3D(40, 100, -407.5);
        E8 = new Point3D(50, 100, -407.5);
        F8 = new Point3D(60, 100, -395);
        G8 = new Point3D(30, 100, -380);
        H8 = new Point3D(60, 100, -380);
        I8 = new Point3D(40, 100, -370);
        J8 = new Point3D(50, 100, -370);
        K8 = new Point3D(45, 110, -387.5);
        L8 = new Point3D(45, 75, -387.5);

        scene._geometries.add(new Polygon(I8, G8, M8, N8, E8, F8, H8, J8).setEmission(new Color(java.awt.Color.WHITE)),
                //UP TRIANGLES
                new Triangle(K8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(K8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(K8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(K8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, E8, F8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(K8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(K8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(K8, J8, I8).setEmission(new Color(color8)).setMaterial(material),
                //DOWN TRIANGLES
                new Triangle(L8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
                new Triangle(L8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
                new Triangle(L8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
                new Triangle(L8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
                new Triangle(L8, E8, F8).setEmission(new Color(color5)).setMaterial(material),
                new Triangle(L8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
                new Triangle(L8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
                new Triangle(L8, J8, I8).setEmission(new Color(color8)).setMaterial(material));


//        double x=-50,y=-50,z=0;
//
//        for (int i = 0; i < 20; i++) {
//            x+=20;
//            y+=12;
//            z-=50;
//
//
//            M8 = new Point3D(30+x, 100+y, -395+z);
//            N8 = new Point3D(40+x, 100+y, -407.5+z);
//            E8 = new Point3D(50+x, 100+y, -407.5+z);
//            F8 = new Point3D(60+x, 100+y, -395+z);
//            G8 = new Point3D(30+x, 100+y, -380+z);
//            H8 = new Point3D(60+x, 100+y, -380+z);
//            I8 = new Point3D(40+x, 100+y, -370+z);
//            J8 = new Point3D(50+x, 100+y, -370+z);
//            K8 = new Point3D(45+x, 110+y, -387.5+z);
//            L8 = new Point3D(45+x, 75+y, -387.5+z);
//
//            scene._geometries.add(new Polygon(I8, G8, M8, N8, E8, F8, H8, J8).setEmission(new Color(java.awt.Color.WHITE)),
//                    //UP TRIANGLES
//                    new Triangle(K8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
//                    new Triangle(K8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
//                    new Triangle(K8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
//                    new Triangle(K8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
//                    new Triangle(K8, E8, F8).setEmission(new Color(color4)).setMaterial(material),
//                    new Triangle(K8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
//                    new Triangle(K8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
//                    new Triangle(K8, J8, I8).setEmission(new Color(color8)).setMaterial(material),
//                    //DOWN TRIANGLES
//                    new Triangle(L8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
//                    new Triangle(L8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
//                    new Triangle(L8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
//                    new Triangle(L8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
//                    new Triangle(L8, E8, F8).setEmission(new Color(color5)).setMaterial(material),
//                    new Triangle(L8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
//                    new Triangle(L8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
//                    new Triangle(L8, J8, I8).setEmission(new Color(color8)).setMaterial(material));
//        }



//        for (int i = 0; i < 30; i++) {
//
//            x-=30;
//            z-=20;
//            y-=9;
//            M8 = new Point3D(30+x, 100+y, -395+z);
//            N8 = new Point3D(40+x, 100+y, -407.5+z);
//            E8 = new Point3D(50+x, 100+y, -407.5+z);
//            F8 = new Point3D(60+x, 100+y, -395+z);
//            G8 = new Point3D(30+x, 100+y, -380+z);
//            H8 = new Point3D(60+x, 100+y, -380+z);
//            I8 = new Point3D(40+x, 100+y, -370+z);
//            J8 = new Point3D(50+x, 100+y, -370+z);
//            K8 = new Point3D(45+x, 110+y, -387.5+z);
//            L8 = new Point3D(45+x, 75+y, -387.5+z);
//
//            scene._geometries.add(new Polygon(I8, G8, M8, N8, E8, F8, H8, J8).setEmission(new Color(java.awt.Color.WHITE)),
//                    //UP TRIANGLES
//                    new Triangle(K8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
//                    new Triangle(K8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
//                    new Triangle(K8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
//                    new Triangle(K8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
//                    new Triangle(K8, E8, F8).setEmission(new Color(color4)).setMaterial(material),
//                    new Triangle(K8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
//                    new Triangle(K8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
//                    new Triangle(K8, J8, I8).setEmission(new Color(color8)).setMaterial(material),
//                    //DOWN TRIANGLES
//                    new Triangle(L8, I8, G8).setEmission(new Color(color1)).setMaterial(material),
//                    new Triangle(L8, G8, M8).setEmission(new Color(color2)).setMaterial(material),
//                    new Triangle(L8, M8, N8).setEmission(new Color(color3)).setMaterial(material),
//                    new Triangle(L8, N8, E8).setEmission(new Color(color4)).setMaterial(material),
//                    new Triangle(L8, E8, F8).setEmission(new Color(color5)).setMaterial(material),
//                    new Triangle(L8, F8, H8).setEmission(new Color(color6)).setMaterial(material),
//                    new Triangle(L8, H8, J8).setEmission(new Color(color7)).setMaterial(material),
//                    new Triangle(L8, J8, I8).setEmission(new Color(color8)).setMaterial(material));
//
//        }



        //we add source light- spot one, with discount coefficients. We wanted the light not to be too strong so we set the coefficient kc quite high and also kl not really low
        scene._lights.add(new SpotLight(new Color(255, 253, 160), new Point3D(0, 64, 70), new Vector(0, -5, 1)).setKc(0.8).setKl(0.08));
        //we add source light- spot one, with discount coefficients. We wanted the light not to be too strong so we set the coefficient kc quite high and also kl not really low
        scene._lights.add(new SpotLight(new Color(255, 253, 160), new Point3D(48, 48, 10), new Vector(-66, -75, 23)).setKc(0.08).setKl(0.3));
        scene._lights.add(new SpotLight(new Color(255, 253, 160), new Point3D(-48, 48, 10), new Vector(66, -71, 23)).setKc(0.08).setKl(0.3));
       // scene._lights.add(new SpotLight(new Color(255, 253, 160), new Point3D(-110, -102, 88), new Vector(125, 102, -18)).setKc(0.08).setKl(0.03));



        ImageWriter imageWriter = new ImageWriter("BVH", 600, 600);
        Render render = new Render()
                .setMultithreading(3)//turn on the threads
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracerBase(new BasicRayTracer(scene));

        scene._geometries.setBVHImprovementOff(false);//turn on the BVH improvement
        scene._geometries.buildHierarchicalBVH();//build the tree for the improvement

        render.renderImage(9);
        render.writeToImage();
    }

    @Test
    public void Mp1() {
        Point3D L = new Point3D(-5, -60, 50);
        Point3D M = new Point3D(-52, -60, 50);
        Point3D N = new Point3D(-27, -66, 80);
        Point3D O = new Point3D(-30, -54, 20);
        Point3D P = new Point3D(-27, -37, 80);
        Point3D Q = new Point3D(-52, -31, 50);
        Point3D R = new Point3D(-30, -25, 20);
        Point3D S = new Point3D(-5, -31, 50);

        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200).setDistance(1000);
        scene.setAmbientLight(new AmbientLight());
        scene._geometries.add(
                //behind
                new Plane(new Point3D(0, 0, 0), new Point3D(50, 50, 0), new Point3D(50, -50, 0))
                        .setEmission(new Color(239, 241, 186)).setMaterial(new Material().setKs(0.3).setnShininess(500).setKd(0.5).setkR(0).setkT(0)),
                //down
                new Plane(new Point3D(50, -50, 0), new Point3D(100, -50, 0), new Point3D(100, -70, 100))
                        .setEmission(new Color(239, 241, 186)).setMaterial(new Material().setKs(0.3).setnShininess(400).setKd(0.5).setkR(0).setkT(0)),
                //up
                new Plane(new Point3D(50, 50, 0), new Point3D(100, 50, 0), new Point3D(100, 70, 100))
                        .setEmission(new Color(239, 241, 186)).setMaterial(new Material().setKs(0.3).setnShininess(350).setKd(0.5).setkR(0).setkT(0)),
                //right
                new Plane(new Point3D(50, -50, 0), new Point3D(50, -100, 0), new Point3D(70, -100, 100))
                        .setMaterial(new Material().setKs(0.8).setnShininess(500).setKd(0).setkR(0.95).setkT(0)),
                //left
                new Plane(new Point3D(-50, 50, 0), new Point3D(-50, 100, 0), new Point3D(-70, 100, 100))
                        .setEmission(new Color(200, 50, 0)).setMaterial(new Material().setKs(0.3).setnShininess(350).setKd(0.8).setkR(0).setkT(0)),

                //the big sphere
                new Sphere(new Point3D(0, -31, 20), 23).setEmission(new Color(53, 85, 204))
                        .setMaterial(new Material().setKd(0.8).setkT(0.02).setkR(0).setKs(1).setnShininess(500)),
                //piramid
                //the front right triangle
                new Triangle(new Point3D(30, -22, 80), new Point3D(30, -70, 100), new Point3D(50, -60, 50)).setEmission(new Color(214, 212, 78))
                        .setMaterial(new Material().setKd(0.8).setkT(0.02).setkR(0).setKs(1).setnShininess(500)),
                //front left triangle
                new Triangle(new Point3D(30, -22, 80), new Point3D(5, -60, 50), new Point3D(30, -70, 100)).setEmission(new Color(214, 212, 78))
                        .setMaterial(new Material().setKd(0.8).setkT(0.02).setkR(0).setKs(1).setnShininess(500)),
                //behind triangle
                new Triangle(new Point3D(30, -22, 80), new Point3D(5, -60, 50), new Point3D(50, -60, 50)).setEmission(new Color(214, 212, 78))
                        .setMaterial(new Material().setKd(0.8).setkT(0.02).setkR(0).setKs(1).setnShininess(500)),
                //under triangle
                new Triangle(new Point3D(5, -60, 50), new Point3D(50, -60, 50), new Point3D(30, -70, 100)).setEmission(new Color(214, 212, 78))
                        .setMaterial(new Material().setKd(0.8).setkT(0.02).setkR(0).setKs(1).setnShininess(500)),
                //front right
                new Polygon(P, S, L, N).setEmission(new Color(94, 199, 92)).setMaterial(new Material().setKd(0.8).setkT(0.02).setkR(0).setKs(1).setnShininess(500)),
                //front left
                new Polygon(Q, P, N, M).setEmission(new Color(94, 199, 92)).setMaterial(new Material().setKd(0.8).setkT(0.02).setkR(0).setKs(1).setnShininess(500)),
                //BEHIND right
                new Polygon(R, S, L, O).setEmission(new Color(94, 199, 92)).setMaterial(new Material().setKd(0.8).setkT(0.02).setkR(0).setKs(1).setnShininess(500)),
                //BEHIND LEFT
                new Polygon(Q, R, O, M).setEmission(new Color(94, 199, 92)).setMaterial(new Material().setKd(0.8).setkT(0.02).setkR(0).setKs(1).setnShininess(500)),
                //UP
                new Polygon(Q, R, S, P).setEmission(new Color(94, 199, 92)).setMaterial(new Material().setKd(0.8).setkT(0.02).setkR(0).setKs(1).setnShininess(500)),
                //DOWN
                new Polygon(M, O, L, N).setEmission(new Color(94, 199, 92)).setMaterial(new Material().setKd(0.8).setkT(0.02).setkR(0).setKs(1).setnShininess(500)));

        //we add source light- spot one, with discount coefficients. We wanted the light not to be too strong so we set the coefficient kc quite high and also kl not really low
        scene._lights.add(new SpotLight(new Color(255, 253, 160), new Point3D(0, 64, 70), new Vector(0, -5, 1)).setKc(0.8).setKl(0.03));
        //we add source light- spot one, with discount coefficients. We wanted the light not to be too strong so we set the coefficient kc quite high and also kl not really low
        scene._lights.add(new SpotLight(new Color(255, 253, 160), new Point3D(48, 48, 10), new Vector(-66, -75, 23)).setKc(0.08).setKl(0.03));
        scene._lights.add(new SpotLight(new Color(255, 253, 160), new Point3D(-48, 48, 10), new Vector(66, -71, 23)).setKc(0.08).setKl(0.03));

        //scene._geometries.setBVHImprovementOff(false);//turn on the BVH improvement
        //scene._geometries.buildHierarchicalBVH();//build the tree for the improvement

        ImageWriter imageWriter = new ImageWriter("Mp1", 600, 600);
        Render render = new Render()
                .setMultithreading(3)//turn on the thread
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracerBase(new BasicRayTracer(scene));
        //Uses improvement of super sampling with parameter = 9
        render.renderImage(9);
        render.writeToImage();
    }


}
