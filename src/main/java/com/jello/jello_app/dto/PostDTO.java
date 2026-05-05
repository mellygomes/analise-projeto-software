package com.jello.jello_app.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private List<ImageDTO> images;
}
