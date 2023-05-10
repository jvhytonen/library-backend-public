package com.rest_api.fs14backend.book;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Data
public class BookDTO {
	private UUID categoryId;
	private UUID authorId;
	private long ISBN;
	private String title;
	private LocalDate publishedDate;
	private String description;
	private String publishers;

}
