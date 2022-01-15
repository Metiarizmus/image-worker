package net.javaguides.springboot.controller;

import net.javaguides.springboot.dto.ImageDto;
import net.javaguides.springboot.enums.BackgroundColor;
import net.javaguides.springboot.model.Image;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.ImageRepository;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.interfaces.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

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

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> uploadImage(
            @RequestParam("height") Double height, @RequestParam("width") Double width,
            @RequestParam("mirrorX") boolean mirrorX, @RequestParam("mirrorY") boolean mirrorY,
            @RequestParam("color") BackgroundColor color,
            @RequestParam("image") MultipartFile file
    ) {


        User user = userRepository.findByEmail(getCurrentlyEmail());

        Image image = new Image(height, width, mirrorX, mirrorY, color);

        try {
            imageService.save(file, user, image);
        } catch (IOException e) {
            logger.info("IOException");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Product Saved With File - " + file.getOriginalFilename(), HttpStatus.OK);
    }

    @GetMapping("/image/show")
    String show(Model map) {
        List<ImageDto> images = imageService.getAllImageForUser(getCurrentlyEmail());
        map.addAttribute("images", images);
        map.addAttribute("email", getCurrentlyEmail());

        return "gallery";
    }


    @GetMapping("/image/imageDetails")
    String showImageDetails(@RequestParam("imageId") Long imageId,
                            Model model) {
        try {

            logger.info("image id in imageDetails request :: " + imageId);

            User user = userRepository.findUserByImageId(imageId);
            logger.info("user email id in imageDetails request :: " + user.getEmail());

            if (imageId != 0) {
                ImageDto imageById = imageService.getImageById(imageId, user.getEmail());

                model.addAttribute("imageDetails", imageById);

                return "imageDetails";
            }
            return "redirect:/image/show";
        } catch (Exception e) {

            return "redirect:/image/show";
        }
    }


}