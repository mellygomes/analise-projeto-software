package com.jello.jello_app.repository;

import com.jello.jello_app.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
