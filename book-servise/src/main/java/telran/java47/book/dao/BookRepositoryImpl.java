package telran.java47.book.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


import org.springframework.stereotype.Repository;

import telran.java47.book.model.Author;
import telran.java47.book.model.Book;

@Repository
public class BookRepositoryImpl implements BookRepository {
	@PersistenceContext
	EntityManager em;

	@Override
	public List<Book> findBooksByAuthorsName(String name) {
		TypedQuery<Book> query = em.createQuery("select b from Book b join  b.authors a where a.name=?1", Book.class);
		query.setParameter(1, name);
		return query.getResultList();
	}

	@Override
	public List<Book> findBooksByPublisherPublisherName(String publisherName) {
		TypedQuery<Book> query = em.createQuery("select b from Book b join  b.publisher p where p.publisherName=?1", Book.class);
		query.setParameter(1, publisherName);
		return query.getResultList();
	}

	@Override
	public List<Author> findAuthorsByIsbn(String isbn) {
		TypedQuery<Author> query = em.createQuery("select b.authors from Book b where b.isbn=?1", Author.class);
		query.setParameter(1, isbn);
		return query.getResultList();
	}

	@Override
	public boolean existsById(String isbn) {
		return em.find(Book.class, isbn) != null;
	}

	@Override
	public Book save(Book book) {
		em.persist(book);
		return book;
	}

	@Override
	public Optional<Book> findById(String isbn) {
		return Optional.ofNullable(em.find(Book.class, isbn));
	}

	@Override
	public void deleteById(String isbn) {
		em.remove(em.find(Book.class, isbn));
	}

}
