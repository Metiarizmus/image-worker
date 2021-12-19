package net.javaguides.springboot.entity;

import lombok.Data;

@Data
public class ResponseImage {
    private String name;
    private String url;
    private String type;
    private Long size;

    private ResponseImage() {

    }

    public ResponseImage(String name, String url, Long size) {
        this.name = name;
        this.url = url;
        this.size = size;
    }
}
