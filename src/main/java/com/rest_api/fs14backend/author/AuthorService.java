package com.rest_api.fs14backend.author;

import com.rest_api.fs14backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rest_api.fs14backend.exceptions.CustomException;

import java.util.List;
import java.util.UUID;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public Author getAuthorById(UUID id) throws Exception {
        Author author = authorRepository.findById(id).orElseThrow(() -> new CustomException("No author with such id found!"));
        return authorRepository.findAuthorById(id);
    }

    public Author createOne(Author newAuthor) {
        List<Author> authorList = authorRepository.findAll();
        for (Author author : authorList) {
            if (newAuthor.getName().equals(author.getName())) {
                throw new IllegalStateException("Author " + newAuthor.getName() + " already exist");
            }
        }
        //AuthorDTO needs to be converted to Author before saving.
        Author authorEntity = new Author(newAuthor.getName(), newAuthor.getDescription());
        return authorRepository.save(authorEntity);
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author delete(UUID id) throws Exception {
        Author authorToDelete = authorRepository.findById(id).orElseThrow(() -> new CustomException("No author with such id found!"));
        authorRepository.delete(authorToDelete);
        return authorToDelete;
    }

    @Transactional
    public Author update(UUID id, AuthorDTO updatedAuthor) throws Exception {
        Author authorToEdit = authorRepository.findById(id).orElseThrow(() -> new CustomException("No author with such id found!"));
        authorToEdit.setName(updatedAuthor.getName());
        authorToEdit.setDescription(updatedAuthor.getDescription());
        return authorToEdit;
    }
}
