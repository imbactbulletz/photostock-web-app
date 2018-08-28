package utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.filters.Caption;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import javax.ws.rs.core.MultivaluedMap;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Base64;

public class FileUtil {


    public static String saveFile(InputStream file, String name) {
        try {
            java.nio.file.Path path = FileSystems.getDefault().getPath("/home/wybz/Desktop/web_projekat/testPhotos/" + name);
            Files.copy(file, path);


            // returning file location
            return path.toString();
        } catch (IOException ie) {
            ie.printStackTrace();
            return null;
        }
    }

    public static BufferedImage readBufferedImage(String path){
        BufferedImage image = null;

        try{
            image = ImageIO.read(new File(path));
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return image;
    }

    public static String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }


    public static BufferedImage putWatermark(String caption, BufferedImage image){
        Font font = new Font("Monospaced", Font.PLAIN, 80);
        Color c = Color.gray;
        Position position = Positions.CENTER;
        int insetPixels = 0;

        Caption filter = new Caption(caption, font, c, position, insetPixels);
        BufferedImage captionedImage = filter.apply(image);

        return captionedImage;
    }

    public static BufferedImage resizeImage(BufferedImage image, int width, int height){
        try {
            return Thumbnails.of(image).size(width, height).asBufferedImage();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encodeImage(BufferedImage image){
        String encodedImage = null;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);

            baos.flush();

            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.getEncoder().encodeToString(imageBytes);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return encodedImage;
    }
}
