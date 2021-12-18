package net.javaguides.springboot.service.interfaces;

import net.javaguides.springboot.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    Image upload(MultipartFile resource) throws IOException;
}
