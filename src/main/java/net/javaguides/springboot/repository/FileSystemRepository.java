package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.User;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class FileSystemRepository {

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
}
