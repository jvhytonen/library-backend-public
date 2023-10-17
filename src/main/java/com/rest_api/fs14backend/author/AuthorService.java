package com.rest_api.fs14backend.author;

import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.book.BookRepository;
import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rest_api.fs14backend.exceptions.CustomException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

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
     // This will check if the author to be deleted is still a foreign key in some book.
        Book bookEntity = bookRepository.findAll()
                .stream()
                .filter(
                        pro -> Objects.equals(pro.getAuthor().getId(), id))
                .findFirst()
                .orElse(null);
        // If the author is still related to a book, it cannot be removed.
        if (null != bookEntity) {
            throw new CustomException("Cannot remove an author that is used by at least one book");
        }  else {
            Author deletedAuthor = authorRepository.findById(id).orElseThrow(() -> new CustomException("No author with such id found"));
            authorRepository.delete(deletedAuthor);
            return deletedAuthor;
        }
    }

    @Transactional
    public Author update(UUID id, AuthorDTO updatedAuthor) throws Exception {
        Author authorToEdit = authorRepository.findById(id).orElseThrow(() -> new CustomException("No author with such id found!"));
        authorToEdit.setName(updatedAuthor.getName());
        authorToEdit.setDescription(updatedAuthor.getDescription());
        return authorToEdit;
    }
    public Author queryByString(String query) {
        return authorRepository.findAuthorByQuery(query);
    }
}
