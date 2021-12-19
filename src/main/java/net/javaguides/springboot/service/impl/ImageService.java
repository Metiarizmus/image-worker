package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.dto.ImageDto;
import net.javaguides.springboot.model.Image;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.ImageRepository;
import net.javaguides.springboot.utils.ImageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private FileSystemService fileSystemService;

    @Autowired
    private ImageRepository imageDbRepository;

    @Autowired
    private ImageUtils imageUtils;

    @Autowired
    private ModelMapper modelMapper;

    public Long save(MultipartFile file, User user) throws IOException {

        String location = fileSystemService.save(file, user.getEmail());

        return imageDbRepository.save(new Image(file.getOriginalFilename(), location, user)).getId();
    }

    public List<ImageDto> getAllImageForUser(String userEmail) {
        List<Image> images = imageDbRepository.getAllByUser_Email(userEmail);

        List<ImageDto> imageDtos = new ArrayList<>();

        for (Image q : images) {
            imageDtos.add(convertToDto(q));
        }

        return imageDtos;
    }


    public Optional<ImageDto> getImageById(Long id, String userEmail) {

        Image image = imageDbRepository.getByIdAndUser_Email(id, userEmail);

        return Optional.of(convertToDto(image));

    }

    private ImageDto convertToDto(Image image) {

        BufferedImage bufferedImage = null;
        byte[] bytesImage = null;
        try {
            bufferedImage = ImageIO.read(new File(image.getLocation()));
            bytesImage = imageUtils.toByteArray(bufferedImage, "jpg");
        } catch (IOException e) {

        }

        ImageDto imageDto = modelMapper.map(image, ImageDto.class);
        imageDto.setImage(bytesImage);

        return imageDto;
    }

}
