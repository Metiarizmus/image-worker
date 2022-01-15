package net.javaguides.springboot.utils;

import net.javaguides.springboot.dto.ImageDto;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.model.Image;
import net.javaguides.springboot.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Component
public class DtoConvert {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ImageUtils imageUtils;

    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public ImageDto convertToDto(Image image) throws UnsupportedEncodingException {

        BufferedImage bufferedImage = null;
        byte[] bytesImage = null;
        try {
            bufferedImage = ImageIO.read(new File(image.getLocation()));
            bytesImage = imageUtils.toByteArray(bufferedImage, "jpg");
        } catch (IOException e) {

        }

        ImageDto imageDto = modelMapper.map(image, ImageDto.class);
        imageDto.setImage(bytesImage);
        imageDto.setEncodeBase64(convertBinImageToString(imageDto.getImage()));

        return imageDto;
    }

    public static String convertBinImageToString(byte[] binImage) {
        if(binImage!=null && binImage.length>0) {
            return Base64.getEncoder().encodeToString(binImage);
        }
        else
            return "";
    }
}
