package com.rest_api.fs14backend.author;

import com.rest_api.fs14backend.category.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {
  private AuthorRepository authorRepository;

  public AuthorService(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  public Author getAuthorById (UUID id) {
    return authorRepository.findAuthorById(id);
  }
  public Author createOne(Author newAuthor) {
    List<Author> authorList = authorRepository.findAll();
    for (Author author : authorList) {
      if (newAuthor.getName().equals(author.getName())) {
        throw new IllegalStateException("Author " + newAuthor.getName() +  " already exist");
      }
    }
    return authorRepository.save(newAuthor);
  }

  public List<Author> findAll()  {
    return authorRepository.findAll();
  }

  public void delete(UUID id) {
    Optional<Author> authorToDelete = authorRepository.findById(id);
    if (authorToDelete.isPresent()) {
      authorRepository.delete(authorToDelete.get());
    } else {
      throw new IllegalStateException("Category with " + id + " not found");
    }
  }
  @Transactional
  public Author update(UUID id, Author updatedAuthor) {
    Optional<Author> authorToEdit = authorRepository.findById(id);
    if (authorToEdit.isPresent()) {
      authorToEdit.get().setName(updatedAuthor.getName());
      authorToEdit.get().setDescription(updatedAuthor.getDescription());
      return updatedAuthor;
    }
    return null;
  }
}
