package telran.java47.book.service;

import java.util.Set;

import telran.java47.book.dto.AuthorDto;
import telran.java47.book.dto.BookDto;

public interface BookService {

	boolean addBook(BookDto bookDto);
	
	BookDto findBookByIsbn(String isbn);
	
	BookDto removeBook(String isbn);
	
	BookDto UpdateBookTitle(String isbn, String title);
	
	Set<BookDto> findBooksByAuthor(String authorName);
	
	Set<BookDto> findBooksByPublisher(String publisher);
	
	Set<AuthorDto> findBookAuthors(String isbn);
	
	Set<String> findPublishersByAuthor(String authorName);
	
	AuthorDto removeAuthor(String authorName);
	
	
}
