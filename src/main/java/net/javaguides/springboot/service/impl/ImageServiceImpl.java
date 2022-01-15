package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.dto.ImageDto;
import net.javaguides.springboot.model.Image;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.ImageRepository;
import net.javaguides.springboot.service.interfaces.ImageService;
import net.javaguides.springboot.utils.DtoConvert;
import net.javaguides.springboot.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageUtils fileSystemService;

    @Autowired
    private ImageRepository imageDbRepository;

    @Autowired
    private DtoConvert dtoConvert;

    public Long save(MultipartFile file, User user, Image image) throws IOException {

        String location = fileSystemService.save(file, user.getEmail());
        image.setUser(user);
        image.setLocation(location);
        image.setImageName(file.getOriginalFilename());

        return imageDbRepository.save(image).getId();
    }

    public List<ImageDto> getAllImageForUser(String userEmail) {
        List<Image> images = imageDbRepository.getAllByUser_Email(userEmail);

        List<ImageDto> imageDtos = new ArrayList<>();

        for (Image q : images) {
            try {
                imageDtos.add(dtoConvert.convertToDto(q));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return imageDtos;
    }

    public ImageDto getImageById(Long id, String userEmail) {

        Image image = imageDbRepository.getByIdAndUser_Email(id, userEmail);

        try {
            return dtoConvert.convertToDto(image);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
