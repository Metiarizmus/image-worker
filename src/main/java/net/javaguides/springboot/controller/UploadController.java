package net.javaguides.springboot.controller;


import net.javaguides.springboot.dto.ImageDto;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.impl.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class UploadController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserRepository userRepository;


    private static String getCurrentlyEmail() {
        String userEmail = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            userEmail = authentication.getName();
        }

        return userEmail;
    }

    

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        User user = userRepository.findByEmail(getCurrentlyEmail());

        try {
            imageService.save(file, user);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

    @GetMapping("/image/show")
    String show(Model map) {
        List<ImageDto> images = imageService.getAllImageForUser(getCurrentlyEmail());
        map.addAttribute("images", images);
        return "gallery";
    }

    @GetMapping("/image/display/{id}")
    void showImage(@PathVariable("id") Long id, HttpServletResponse response) throws ServletException, IOException {
        Optional<ImageDto> imageGallery = imageService.getImageById(id, getCurrentlyEmail());
        response.setContentType("image/jpg");
        response.getOutputStream().write(imageGallery.get().getImage());
        response.getOutputStream().close();
    }

}