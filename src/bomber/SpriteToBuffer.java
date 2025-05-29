package bomber;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class SpriteToBuffer {
    public static BufferedImage convertir(String ruta)
    {
        try {
            return ImageIO.read(SpriteToBuffer.
                    class.getResource(ruta));
        } catch (IOException ex) {
            return null;
        }
    }
}
