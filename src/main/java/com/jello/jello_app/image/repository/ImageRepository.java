package com.jello.jello_app.image.repository;

import com.jello.jello_app.image.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
