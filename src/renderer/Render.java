package renderer;

import elements.Camera;
import jdk.jshell.spi.ExecutionControl;
import jdk.jshell.spi.ExecutionControl.*;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.MissingResourceException;

/**
 * class for create the matrix of the colors of the image
 */
public class Render {
    ImageWriter _imageWriter;
    Camera _camera;
    RayTracerBase _rayTracerBase;

    /**
     *
     * @param imageWriter
     * @return
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    /**
     *
     * @param camera
     * @return
     */
    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }

    /**
     *
     * @param rayTracerBase
     * @return
     */
    public Render setRayTracerBase(RayTracerBase rayTracerBase) {
        _rayTracerBase = rayTracerBase;
        return this;
    }
    /**
     * check the field of the class
     */
    public void renderImage(int numberOfSamples) {
        try {
            if (_imageWriter == null) {
                throw new MissingResourceException("image writer is empty", ImageWriter.class.getName(), "");
            }
            if (_camera == null) {
                throw new MissingResourceException("camera is empty", Camera.class.getName(), "");
            }
            if (_rayTracerBase == null) {
                throw new MissingResourceException("ray Tracer Base is empty", RayTracerBase.class.getName(), "");
            }

            Ray ray;
            Color c = new Color(0, 0, 0);
            int Nx=_imageWriter.getNx();
            int Ny=_imageWriter.getNy();
            for (int i = 0; i < Ny; i++) {
                for (int j = 0; j < Nx; j++) {
                    for (int ii = 0; ii < numberOfSamples; ii++) {
                        for (int jj = 0; jj < numberOfSamples; jj++) {
                            ray = _camera.constructRayThroughRandomPixel(Nx, Ny, j, i,numberOfSamples,ii,jj);
                            c=c.add(_rayTracerBase.traceRay(ray));
                        }
                    }
                    _imageWriter.writePixel(j, i,c.reduce(numberOfSamples*numberOfSamples));
                }

            }

        } catch (MissingResourceException missingResourceException) {
            throw new UnsupportedOperationException("the function is partly implements "+missingResourceException.getClassName());
            //throw new ExecutionControl.NotImplementedException("the function is partly implements"+missingResourceException.getClassName());
        }
    }
    /**
     * check the field of the class
     */
    public void renderImage() {
        try {
            if (_imageWriter == null) {
                throw new MissingResourceException("image writer is empty", ImageWriter.class.getName(), "");
            }
            if (_camera == null) {
                throw new MissingResourceException("camera is empty", Camera.class.getName(), "");
            }
            if (_rayTracerBase == null) {
                throw new MissingResourceException("ray Tracer Base is empty", RayTracerBase.class.getName(), "");
            }

            Ray ray;
            Color c = new Color(0, 0, 0);
            int Nx=_imageWriter.getNx();
            int Ny=_imageWriter.getNy();
            for (int i = 0; i < Ny; i++) {
                for (int j = 0; j < Nx; j++) {
                            ray = _camera.constructRayThroughPixel(Nx, Ny, j, i);
                            _imageWriter.writePixel(j, i, _rayTracerBase.traceRay(ray));
                }

            }

        } catch (MissingResourceException missingResourceException) {
            throw new UnsupportedOperationException("the function is partly implements "+missingResourceException.getClassName());
            //throw new ExecutionControl.NotImplementedException("the function is partly implements"+missingResourceException.getClassName());
        }
    }

    /**
     * print the grid of the image
     * @param interval the size of every square
     * @param color the color of the grid
     */
    public void printGrid(int interval, Color color) {
        if (_imageWriter == null) {
            throw new MissingResourceException("image writer is empty", ImageWriter.class.getName(), "");
        }
        int Nx = _imageWriter.getNx();
        int Ny = _imageWriter.getNy();
        for (int i = 0; i < Nx; i ++) {
            for (int j = 0; j < Ny; j ++) {
                if(i%interval==0||j%interval==0) {
                    _imageWriter.writePixel(i, j, color);
                }
            }
        }
    }

    /**
     * write the image to the image
     */
    public void writeToImage() {
        if (_imageWriter == null) {
            throw new MissingResourceException("image writer is empty", ImageWriter.class.getName(), "");
        }
        _imageWriter.writeToImage();
    }

}
