package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.model.Image;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.FileSystemRepository;
import net.javaguides.springboot.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

@Service
public class FileLocationService {

    @Autowired
    private FileSystemRepository fileSystemRepository;
    @Autowired
    private ImageRepository imageDbRepository;

    public Long save(MultipartFile file, User user) throws IOException {

        String location = fileSystemRepository.save(file, user.getEmail());

        return imageDbRepository.save(new Image(file.getOriginalFilename(), location, user)).getId();
    }


    public FileSystemResource find(Long imageId) {
        Image image = imageDbRepository.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return fileSystemRepository.findInFileSystem(image.getLocation());
    }

    public Stream<Image> getAllFiles() {
        return imageDbRepository.findAll().stream();
    }

}
