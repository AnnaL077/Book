package telran.java47.book.dao;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import telran.java47.book.model.Author;
import telran.java47.book.model.Book;





public interface BookRepository extends PagingAndSortingRepository<Book, String> {
	
	List<Book> findBooksByAuthorsName(String name);
	
	List<Book> findBooksByPublisherPublisherName(String publisherName);
	
	@Query("select b.authors from Book b where b.isbn=?1")
	List<Author> findAuthorsByIsbn(String isbn);
	

}
