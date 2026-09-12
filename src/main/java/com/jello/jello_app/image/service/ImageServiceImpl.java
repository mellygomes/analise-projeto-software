package com.jello.jello_app.image.service;

import com.jello.jello_app.image.dto.ImageDTO;
import com.jello.jello_app.image.model.Image;
import com.jello.jello_app.post.model.Post;
import com.jello.jello_app.image.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public List<ImageDTO> saveImageForPost(List<MultipartFile> files, Post post) {
        List<ImageDTO> savedImagesDTO = new ArrayList<>();
        String buildDownloadUrl = "/api/v1/image/download/";

        for (MultipartFile file : files) {
            try{
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(file.getBytes());

                image.setPost(post);

                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadUrl(buildDownloadUrl +  savedImage.getId());
                imageRepository.save(savedImage);

                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setId(savedImage.getId());
                imageDTO.setFileName(savedImage.getFileName());
                imageDTO.setDownloadUrl(savedImage.getDownloadUrl());

                savedImagesDTO.add(imageDTO);
            } catch (IOException e) {
                throw new RuntimeException("Error processing image: " + file.getOriginalFilename(), e);
            }
        }

        return savedImagesDTO;
    }

    @Override
    public Image getImageById(Long imageId){
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found!"));
    }
}
