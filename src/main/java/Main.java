import net.javaguides.springboot.service.impl.FileLocationService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

        FileLocationService d = new FileLocationService();
        System.out.println(d.getAllFiles());

    }
}
