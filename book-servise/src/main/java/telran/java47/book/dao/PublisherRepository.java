package telran.java47.book.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import telran.java47.book.model.Publisher;

public interface PublisherRepository extends PagingAndSortingRepository<Publisher, String> {
	
	@Query("select p.publisherName from Book b join b.authors a join b.publisher p where a.name = ?1")
	List<String> findPubByAuther(String authorName);
	

}
