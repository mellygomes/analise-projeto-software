package com.jello.jello_app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;

@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();
}
