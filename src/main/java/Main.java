import net.javaguides.springboot.service.impl.ImageServiceImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ImageServiceImpl imageService = new ImageServiceImpl();
        System.out.println(imageService.getAllImageForUser("user@mail.ru"));
    }
}
