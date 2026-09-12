package com.jello.jello_app.post.model;

import com.jello.jello_app.common.model.Auditable;
import com.jello.jello_app.comment.model.Comment;
import com.jello.jello_app.image.model.Image;
import com.jello.jello_app.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post extends Auditable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    private int aiCount = 0;

    public void incrementAiCount() {
        this.aiCount++;
    }

    public void decrementAiCount() {
        if (this.aiCount > 0) {
            this.aiCount--;
        }
    }
}
