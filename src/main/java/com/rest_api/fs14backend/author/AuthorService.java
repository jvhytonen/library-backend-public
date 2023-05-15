package com.rest_api.fs14backend.author;

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
    Optional<Author> author = Optional.ofNullable(authorRepository.findAuthorById(id));
    if (author.isPresent()) {
      return authorRepository.findAuthorById(id);
    }
    else {
      throw new IllegalStateException("Category with " + id + " not found");
    }
  }
  public Author createOne(Author newAuthor) {
    List<Author> authorList = authorRepository.findAll();
    for (Author author : authorList) {
      if (newAuthor.getName().equals(author.getName())) {
        throw new IllegalStateException("Author " + newAuthor.getName() +  " already exist");
      }
    }
    //AuthorDTO needs to be converted to Author before saving.
    Author authorEntity = new Author(newAuthor.getName(), newAuthor.getDescription());
    return authorRepository.save(authorEntity);
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
  public Author update(UUID id, AuthorDTO updatedAuthor) throws Exception {
    Author authorToEdit = authorRepository.findById(id).orElseThrow(() -> new Exception("No category with such id found!"));
      authorToEdit.setName(updatedAuthor.getName());
      authorToEdit.setDescription(updatedAuthor.getDescription());
      return authorToEdit;
  }
}
