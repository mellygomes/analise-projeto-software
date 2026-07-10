package com.jello.jello_app.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends Auditable{

    private String firstName;
    private String lastName;
    private String bio;
    private Boolean enabled;

    @Column(unique = true)
    private String email;

    private LocalDateTime lastLogin;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(columnDefinition = "BYTEA")
    private byte[] profilePicture;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Collection<Role> roles = new HashSet<>();
}
