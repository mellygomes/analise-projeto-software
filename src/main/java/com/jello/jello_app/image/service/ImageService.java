package com.jello.jello_app.image.service;

import com.jello.jello_app.image.dto.ImageDTO;
import com.jello.jello_app.image.model.Image;
import com.jello.jello_app.post.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<ImageDTO> saveImageForPost(List<MultipartFile> files, Post post);
    Image getImageById(Long imageId);
}
