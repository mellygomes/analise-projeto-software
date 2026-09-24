package com.jello.jello_app.image.mapper;

import com.jello.jello_app.image.dto.ImageDTO;
import com.jello.jello_app.image.model.Image;
import com.jello.jello_app.post.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ImageMapper {
    public static ImageDTO toDto(Image image) {

        String downloadUrl = "/api/v1/image/download/";
        if (image.getId() != null) {
            downloadUrl = downloadUrl + image.getId();
        }

        return ImageDTO.builder()
                .id(image.getId())
                .fileName(image.getFileName())
                .downloadUrl(downloadUrl)
                .build();
    }

    public static Image toEntity(MultipartFile file, Post post) throws IOException {
        return Image.builder()
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .image(file.getBytes())
                .post(post)
                .build();
    }
}
