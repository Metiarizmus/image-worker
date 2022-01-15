package net.javaguides.springboot.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Component
public class ImageUtils {

    public String save(MultipartFile file, String emailUser) throws IOException {

        File theDir = new File("G://" + emailUser);

        if (!theDir.exists()) {
            try{
                theDir.mkdir();
            }
            catch(SecurityException se){

            }
        }

        byte[] bytes = file.getBytes();
        Path path = Paths.get(theDir.getPath()+"//" + new Date().getTime() + "-" + file.getOriginalFilename());
        Files.write(path, bytes);

        return path.toAbsolutePath().toString();
    }


    public FileSystemResource findInFileSystem(String location) {
        try {
            return new FileSystemResource(Paths.get(location));
        } catch (Exception e) {
            // Handle access or file not found problems.
            throw new RuntimeException();
        }
    }


    // convert BufferedImage to byte[]
    public byte[] toByteArray(BufferedImage bi, String format) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;

    }

    // convert byte[] to BufferedImage
    public BufferedImage toBufferedImage(byte[] bytes)
            throws IOException {

        InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage bi = ImageIO.read(is);
        return bi;

    }

}
