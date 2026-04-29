package com.jello.jello_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;
    private String downloadUrl;

    @Lob
    @Column(columnDefinition = "BYTEA")
    private byte[] image;

//    @ManyToOne
//    @JoinColumn(name = "post_id")
//    private Post post;
}
