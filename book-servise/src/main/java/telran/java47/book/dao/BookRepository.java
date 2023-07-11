package telran.java47.book.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;


import telran.java47.book.model.Author;
import telran.java47.book.model.Book;

public interface BookRepository {
	
	List<Book> findBooksByAuthorsName(String name);
	
	List<Book> findBooksByPublisherPublisherName(String publisherName);
	
	@Query("select b.authors from Book b where b.isbn=?1")
	List<Author> findAuthorsByIsbn(String isbn);

	boolean existsById(String isbn);
	
	Book save(Book book);
	
	Optional<Book> findById(String isbn);

	void deleteById(String isbn);
	

}
