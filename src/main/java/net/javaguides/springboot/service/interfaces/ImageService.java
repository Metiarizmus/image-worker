package net.javaguides.springboot.service.interfaces;

import net.javaguides.springboot.dto.ImageDto;
import net.javaguides.springboot.model.Image;
import net.javaguides.springboot.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ImageService {
    Long save(MultipartFile file, User user, Image image) throws IOException;

    List<ImageDto> getAllImageForUser(String userEmail);

    Optional<ImageDto> getImageById(Long id, String userEmail);

}
