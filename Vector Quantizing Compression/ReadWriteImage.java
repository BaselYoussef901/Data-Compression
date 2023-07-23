import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ReadWriteImage {
    //read 2D int pixels from image file
    public static int[][] readImage(String filePath) {
        File file = new File(filePath);
        BufferedImage Image;
        int width, height;
        try {
            Image = ImageIO.read(file);
            width = Image.getWidth();
            height = Image.getHeight();
            int[][] imagePixels = new int[height][width];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int RGB = Image.getRGB(x, y);
                    int a   = (RGB & 0xFF000000) >> 24;     //Alpha
                    int r   = (RGB & 0x00FF0000) >> 16;     //Red
                    int g   = (RGB & 0x0000FF00) >>  8;     //Green
                    int b   = (RGB & 0x000000FF) >>  0;     //Blue
                    imagePixels[y][x] = r;
                }
            }
            return imagePixels;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeImage(int[][] imagePixels, String outputFilePath) {
        File FileOut = new File(outputFilePath);
        int height = imagePixels.length;
        int width = imagePixels[0].length;
        BufferedImage Image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int value = -1<<24;
                value = 0xff000000 | (imagePixels[y][x] << 16) | (imagePixels[y][x] << 8) | (imagePixels[y][x]);
                Image.setRGB(x, y, value);
            }
        }
        try {
            ImageIO.write(Image, "jpg", FileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}