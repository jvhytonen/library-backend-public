package com.rest_api.fs14backend.author;

import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    public static Author newAuthor(AuthorDTO author){
        return new Author(
                author.getName(),
                author.getDescription()
        );
    }
}
