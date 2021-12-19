package net.javaguides.springboot.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Component
public class ImageUtils {
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

//    public static void main(String[] args) throws IOException {
//
//        BufferedImage bi = ImageIO.read(new File("G:/user@mail.ru/1639599857954-pulpitrock.jpg"));
//
//        // convert BufferedImage to byte[]
//        byte[] bytes = toByteArray(bi, "png");
//
//        //encode the byte array for display purpose only, optional
//        String bytesBase64 = Base64.encodeBase64String(bytes);
//
//        System.out.println(bytesBase64);
//
//        // decode byte[] from the encoded string
//        byte[] bytesFromDecode = Base64.decodeBase64(bytesBase64);
//
//        // convert the byte[] back to BufferedImage
//        BufferedImage newBi = toBufferedImage(bytesFromDecode);
//
//        // save it somewhere
//        ImageIO.write(newBi, "png", new File("G:/user@mail.ru/1639599857954-pulpitrock.jpg"));
//
//    }
}
