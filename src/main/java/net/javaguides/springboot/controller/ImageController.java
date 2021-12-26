package net.javaguides.springboot.controller;


import net.javaguides.springboot.dto.ImageDto;
import net.javaguides.springboot.enums.BackgroundColor;
import net.javaguides.springboot.model.Image;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.interfaces.ImageService;
import org.apache.log4j.Logger;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class ImageController {

    private final static Logger loger = Logger.getLogger(ImageController.class);

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
                                            @RequestParam("image") MultipartFile file,
                                            Model model
    ) {

        if (file.getOriginalFilename() == null || file.getOriginalFilename().contains("..")) {
            model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
            return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + file.getOriginalFilename(), HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByEmail(getCurrentlyEmail());


        Image image = new Image(height, width, mirrorX, mirrorY, color);

        try {
            imageService.save(file, user, image);
        } catch (IOException e) {
            e.printStackTrace();
            //log.info("Exception: " + e);
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

    @GetMapping("/image/display")
    void showImage(@RequestParam("imageId") Long imageId,
                   @RequestParam("userEmail") String userEmail,
                   HttpServletResponse response) throws  IOException {

        loger.info("image id in display request :: " + imageId);
        loger.info("user email id in display request :: " + userEmail);

        Optional<ImageDto> imageGallery = imageService.getImageById(imageId, userEmail);
        response.setContentType("image/jpg");
        response.getOutputStream().write(imageGallery.get().getImage());
        response.getOutputStream().close();
    }

    @GetMapping("/image/imageDetails")
    String showImageDetails(@RequestParam("imageId") Long imageId,
                            @RequestParam("userEmail") String userEmail,
                            Optional<ImageDto> imageGallery, Model model) {
        try {

            loger.info("image id in imageDetails request :: " + imageId);
            loger.info("user email id in imageDetails request :: " + userEmail);

            if (imageId != 0) {
                imageGallery = imageService.getImageById(imageId, userEmail);

                //log.info("products :: " + imageGallery);
                if (imageGallery.isPresent()) {
                    model.addAttribute("id", imageGallery.get().getId());
                    model.addAttribute("name", imageGallery.get().getImageName());
                    model.addAttribute("height", imageGallery.get().getHeight());
                    model.addAttribute("width", imageGallery.get().getWidth());
                    model.addAttribute("mirrorX", imageGallery.get().isMirrorX());
                    model.addAttribute("mirrorY", imageGallery.get().isMirrorY());
                    model.addAttribute("color", imageGallery.get().getColor());
                    model.addAttribute("email", userEmail);
                    return "imageDetails";
                }
                return "redirect:/";
            }
            return "redirect:/";
        } catch (Exception e) {

            return "redirect:/";
        }
    }

}