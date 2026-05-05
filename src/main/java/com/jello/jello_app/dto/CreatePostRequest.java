package com.jello.jello_app.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CreatePostRequest {
    private String title;
    private String content;
}
