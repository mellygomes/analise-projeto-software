package com.jello.jello_app.post.mapper;

import com.jello.jello_app.post.dto.PostDTO;
import com.jello.jello_app.post.model.Post;

public class PostMapper {
    public static PostDTO toDto(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
