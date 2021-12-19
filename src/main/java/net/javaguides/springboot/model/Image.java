package net.javaguides.springboot.model;

import lombok.Getter;
import lombok.Setter;
import net.javaguides.springboot.enums.BackgroundColor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "Image")
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "location")
    private String location;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Image() {

    }

    public Image(String fileName, String location, User user) {
        this.imageName = fileName;
        this.location = location;
        this.user = user;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id) && Objects.equals(imageName, image.imageName) && Objects.equals(location, image.location) && Objects.equals(user, image.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imageName, location, user);
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", fileName='" + imageName + '\'' +
                ", location='" + location + '\'' +
                ", user=" + user +
                '}';
    }
}

//    @Column(name = "height")
//    private Double height;
//
//    @Column(name = "width")
//    private Double width;
//
//    @Column(name = "mirrorX")
//    private boolean mirrorX;
//
//    @Column(name = "mirrorY")
//    private boolean mirrorY;
//
//    @Column(name = "color")
//    private BackgroundColor color;