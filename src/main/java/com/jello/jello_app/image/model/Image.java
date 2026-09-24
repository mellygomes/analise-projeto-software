package com.jello.jello_app.image.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jello.jello_app.post.model.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;
//    private String downloadUrl;

    @Column(columnDefinition = "BYTEA")
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;
}
