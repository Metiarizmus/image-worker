package net.javaguides.springboot.controller;

import net.javaguides.springboot.dto.ImageDto;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.service.interfaces.AdminService;
import net.javaguides.springboot.service.interfaces.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/admin/listUsers")
    String getListUser(Model model) {
        List<UserDto> users = adminService.findAllUsers();
        model.addAttribute("users", users);
        return "listUser";
    }

    @GetMapping("/admin/banUser/{id}")
    String banUser(@PathVariable("id") Long id) {
        log.info("id for ban user :: " + id);
        adminService.blocked(id);
        return "redirect:/admin/listUsers";
    }

    @GetMapping("/admin/unbanUser/{id}")
    String unbanUser(@PathVariable("id") Long id) {
        log.info("id for unban user :: " + id);
        adminService.unblocked(id);
        return "redirect:/admin/listUsers";
    }

    @GetMapping("/admin/image/show")
    String showAdmin(@RequestParam("emailUser") String emailUser, Model map) {
        log.info("email for show gallery from admin :: " + emailUser);
        List<ImageDto> images = imageService.getAllImageForUser(emailUser);
        map.addAttribute("images", images);
        map.addAttribute("email", emailUser);
        return "gallery";
    }



}
