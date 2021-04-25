package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    /**
     * test that checks the creating of image
     */
    @Test
    void testWriteToImage() {
        ImageWriter imageWriter=new ImageWriter("blueTest",800,500);
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 500; j++) {
                if(i%50==0||j%50==0){
                    imageWriter.writePixel(i,j,Color.BLACK);
                }
                else {
                    imageWriter.writePixel(i, j, new Color(0, 0, 255d));
                }
            }
        }
        imageWriter.writeToImage();
    }

}