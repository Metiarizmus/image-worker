package net.javaguides.springboot.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
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
    private String fileName;

    @Column(name = "location")
    private String location;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Image() {

    }

    public Image(String fileName, String location, User user) {
        this.fileName = fileName;
        this.location = location;
        this.user = user;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id) && Objects.equals(fileName, image.fileName) && Objects.equals(location, image.location) && Objects.equals(user, image.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileName, location, user);
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", location='" + location + '\'' +
                ", user=" + user +
                '}';
    }
}
