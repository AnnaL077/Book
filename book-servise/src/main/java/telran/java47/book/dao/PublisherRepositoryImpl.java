package telran.java47.book.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import telran.java47.book.model.Publisher;

@Repository
public class PublisherRepositoryImpl implements PublisherRepository {
	
	@PersistenceContext
	EntityManager em;

	@Override
	public List<String> findPubByAuther(String authorName) {
		TypedQuery<String> query = em.createQuery("select p.publisherName from Book b join b.authors a join b.publisher p where a.name = ?1", String.class);
		query.setParameter(1, authorName);
		return query.getResultList();
	}

	@Override
	public Stream<Publisher> findDistinctByBooksAuthorsName(String authorName) {
		TypedQuery<Publisher> query = em.createQuery("\"select p from Book b join b.authors a join b.publisher p where a.name = ?1", Publisher.class);
		query.setParameter(1, authorName);
		return query.getResultStream();
	}

	@Override
	public Optional<Publisher> findById(String publisher) {
		return Optional.ofNullable(em.find(Publisher.class, publisher));
	}

	@Override
	//@Transactional
	public Publisher save(Publisher publisher) {
		em.persist(publisher);
		//em.merge(publisher);
		return publisher;
	}

}