package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.category.Category;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
	public Book newBook(BookDTO book, Category category, Author author){
		return new Book(
				book.getIsbn(),
		book.getTitle(),
		book.getPublishedDate(),
		book.getDescription(),
		book.getPublishers(),
		category, author);
	}

}
