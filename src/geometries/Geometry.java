package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

import java.util.Map;

/**
 * interface of all the geometry
 */
public abstract class Geometry implements Intersectable{
    protected Color _emission=Color.BLACK;
    private Material _material=new Material();

    /**
     * getter for the emission
     * @return
     */
    public Color getEmission() {
        return _emission;
    }

    //getter for the material
    public Material getMaterial() {
        return _material;
    }

    public Geometry setMaterial(Material material) {
        _material = material;
        return this;
    }

    /**
     * setter for the emission
     * @param emission
     * @return the object himself
     */
    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }

    /**
     * function that calculate the normal of every geometry
     * @param point
     * @return
     */
   public abstract Vector getNormal(Point3D point);
}
