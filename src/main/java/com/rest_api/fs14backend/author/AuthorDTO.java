package com.rest_api.fs14backend.author;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AuthorDTO {
    private UUID id;
    private String name;
    private String description;

    public AuthorDTO(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}