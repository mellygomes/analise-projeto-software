package com.jello.jello_app.dto;

import com.jello.jello_app.model.Image;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostDTO {
    private Long id;
    private String title;
    private String content;
}
