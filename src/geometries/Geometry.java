package geometries;

import primitives.*;

import java.util.Map;

/**
 * interface of all the geometry
 */
public abstract class Geometry implements Intersectable{
    protected Color _emission=Color.BLACK;
    private Material _material=new Material();

    //field of the box around the geometry
    protected Box _box=new Box();


    /**
     * function that return the box of finite geometries
     * @return
     */
  public abstract Box getBox();//{
//        if (this.getUpRightBehind().getX()==Double.POSITIVE_INFINITY){
//            throw new UnsupportedOperationException("The Geometry is infinity");
//        }
//        return this;
//    }

    /**
     * getter for the emission
     * @return the color emission
     */
    public Color getEmission() {
        return _emission;
    }

    /**
     * getter for the material
     * @return the material
     */
    public Material getMaterial() {
        return _material;
    }

    /**
     * setter for the material, like builder pattern
     * @param material the material
     * @return the geometry
     */
    public Geometry setMaterial(Material material) {
        _material = material;
        return this;
    }

    /**
     * setter for the emission, like builder pattern
     * @param emission the emission of the geometry
     * @return the object himself, the geometry
     */
    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }

    /**
     * function that calculate the normal of every geometry
     * @param point the point we want to calculate the normal for
     * @return the normal
     */
   public abstract Vector getNormal(Point3D point);

}
