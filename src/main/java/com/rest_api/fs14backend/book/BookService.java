package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.author.AuthorService;
import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.category.CategoryService;
import com.rest_api.fs14backend.exceptions.NotFoundException;
import com.rest_api.fs14backend.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookMapper bookMapper;
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Page request-object.
    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }

    public Book createOne(BookDTO newBook) throws Exception {
        UUID categoryId = newBook.getCategoryId();
        Category category = categoryService.findById(categoryId);
        UUID authorId = newBook.getAuthorId();
        Author author = authorService.getAuthorById(authorId);
        Date yearPublished = formatPublishYear(newBook.getYearPublished());
        Book book = bookMapper.newBook(newBook, category, author, yearPublished);
        return bookRepository.save(book);
    }

    public Book getBookById(UUID id) throws Exception {
        Book bookEntity = bookRepository.findById(id).orElseThrow(() -> new CustomException("No book with such id found!"));
        return bookEntity;
    }

    public Book deleteBook(UUID id) {
        Book bookToDelete = bookRepository.findById(id).orElseThrow(() -> new CustomException("Book with such id not found"));
        bookRepository.delete(bookToDelete);
        return bookToDelete;
    }

    @Transactional
    public Book updateBook(UUID id, BookDTO updatedBook) throws Exception {
        Book bookToEdit = bookRepository.findById(id).orElseThrow(() -> new CustomException("No book with such id found!"));
        Author author = authorService.getAuthorById(updatedBook.getAuthorId());
        bookToEdit.setAuthor(author);
        Category category = categoryService.findById(updatedBook.getCategoryId());
        bookToEdit.setCategory(category);
        bookToEdit.setDescription(updatedBook.getDescription());
        bookToEdit.setIsbn(updatedBook.getIsbn());
        Date yearPublished = formatPublishYear(updatedBook.getYearPublished());
        bookToEdit.setYearPublished(yearPublished);
        bookToEdit.setTitle(updatedBook.getTitle());
        bookToEdit.setPublisher(updatedBook.getPublisher());
        return bookToEdit;
    }
    public Date formatPublishYear(String year) throws ParseException {
        // We need only a year.
        SimpleDateFormat yearDate = new SimpleDateFormat("yyyy");
        yearDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        return yearDate.parse(year);
    }

    public List<Book> getBooksByAuthorId(UUID id) {
        return bookRepository.findByAuthorId(id);
    }

    public List<Book> getBooksByCategoryId(UUID id) {return bookRepository.findByCategoryId(id);}

    public Page<Book> getBooksByPage(int page, int size, String query) {
        Pageable pageRequest = createPageRequestUsing(page, size);
        List<Book> books = queryItems(query);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), books.size());
        List<Book> pageContent = books.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, books.size());
    }

    public List<Book> queryItems(String query) {
        // If the string is empty, we search all books.
        if (query == null || query.isEmpty()) {
            List<Book> allBooks = bookRepository.findAll();
            return allBooks;
        }
        // Otherwise we find all authors, categories and books, where the query exists.
        else {
            List<Book> booksWithinQuery = bookRepository.searchBooksByQuery(query);
            List<Book> booksByAuthorsWithinQuery = queryAuthors(query);
            List<Book> booksByCategoriesWithinQuery = queryCategories(query);
            List<Book> queriedBooks = combineQueriedItems(booksWithinQuery, booksByAuthorsWithinQuery, booksByCategoriesWithinQuery);
            return queriedBooks;
        }
    }
    public List<Book> queryAuthors(String query) {
        List<Book> booksByQueriedAuthors = new ArrayList<>();
        // We pick the author if the query exists in his/hers name.
        List<Author> matchingAuthors = authorService.queryByString(query);

        // And find books that are written by this author.
        for(Author author: matchingAuthors) {
            List<Book> booksByAuthor = getBooksByAuthorId(author.getId());
            booksByQueriedAuthors.addAll(booksByAuthor);
        }
        return booksByQueriedAuthors;
    }
    public List<Book> queryCategories(String query) {
        List<Book> booksByQueriedCategories = new ArrayList<>();
        // We pick the category if the query exists in the category name.
        List<Category> matchingCategories = (List<Category>) categoryService.queryByString(query);
        // And find books that are in this category.
        for(Category category: matchingCategories) {
            List<Book> booksByCategory = getBooksByCategoryId(category.getId());
            booksByQueriedCategories.addAll(booksByCategory);
        }
        return booksByQueriedCategories;
    }
    public List<Book> combineQueriedItems(List<Book> queriedBooks, List<Book> queriedAuthors, List<Book> queriedCategories) {
        List<Book> allQueriedBooks = new ArrayList<>();
        Set<UUID> uniqueBookIds = new HashSet<>();
        for(List<Book> list : Arrays.asList(queriedBooks, queriedAuthors, queriedCategories)){
            for(Book book : list) {
                if(uniqueBookIds.add(book.getId())){
                    allQueriedBooks.add(book);
                }
            }
        }
        return allQueriedBooks;
    }
}
