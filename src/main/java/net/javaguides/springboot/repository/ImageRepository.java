package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

     List<Image> getAllByUser_Email(String email);

     Image getByIdAndUser_Email(Long id, String userEmail);

}
