package com.jello.jello_app.service;

import com.jello.jello_app.dto.CreatePostRequest;
import com.jello.jello_app.dto.ImageDTO;
import com.jello.jello_app.model.Image;
import com.jello.jello_app.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<ImageDTO> saveImageForPost(List<MultipartFile> files, Post post);
    List<ImageDTO> saveImages(List<MultipartFile> files);
    Image getImageById(Long imageId);
}
