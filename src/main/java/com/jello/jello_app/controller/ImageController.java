package com.jello.jello_app.controller;

import com.jello.jello_app.dto.ApiResponse;
import com.jello.jello_app.dto.ImageDTO;
import com.jello.jello_app.model.Image;
import com.jello.jello_app.repository.ImageRepository;
import com.jello.jello_app.service.ImageService;
import jakarta.persistence.PostRemove;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files){
        try{
            List<ImageDTO> imageDTOs = imageService.saveImages(files);
            return ResponseEntity.ok(new ApiResponse("Upload success!", imageDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Upload failed!", e.getMessage()));
        }
    }

    @GetMapping("/{imageId}/download")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId){
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage());

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }
}
