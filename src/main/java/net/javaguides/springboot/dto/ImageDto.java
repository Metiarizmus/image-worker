package net.javaguides.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaguides.springboot.enums.BackgroundColor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    private Long id;
    private String imageName;
    private String location;
    private byte[] image;
    private Double height;

    private Double width;

    private boolean mirrorX;

    private boolean mirrorY;

    private BackgroundColor color;
}



