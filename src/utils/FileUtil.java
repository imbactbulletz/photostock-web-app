package utils;

import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;

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

}
