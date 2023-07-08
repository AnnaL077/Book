package telran.java47.book.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import telran.java47.book.dao.AuthorRepository;
import telran.java47.book.dao.BookRepository;
import telran.java47.book.dao.PublisherRepository;
import telran.java47.book.dto.AuthorDto;
import telran.java47.book.dto.BookDto;
import telran.java47.book.dto.exceptions.EntityNotFoundException;
import telran.java47.book.model.Author;
import telran.java47.book.model.Book;
import telran.java47.book.model.Publisher;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	final BookRepository bookRepository;
	final AuthorRepository authorRepository;
	final PublisherRepository publisherRepository;
	final ModelMapper modelMapper;
	
	@Override
	@Transactional
	public boolean addBook(BookDto bookDto) {
		if (bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}

		Publisher publisher =publisherRepository.findById(bookDto.getPublisher())
				.orElse(publisherRepository.save(new Publisher(bookDto.getPublisher())));
		
		Set<Author> authors = bookDto.getAuthors().stream()
				.map(a -> authorRepository.findById(a.getName())
						.orElse(authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
				.collect(Collectors.toSet());
		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;
	}

	@Override
	public BookDto findBookByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return modelMapper.map(book, BookDto.class);
	}
	
	@Override
	@Transactional
	public BookDto removeBook(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		bookRepository.delete(book);
		return  modelMapper.map(book, BookDto.class);
	}
	
	@Override
	@Transactional(readOnly = true)
	public BookDto UpdateBookTitle(String isbn, String title) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		book.setTitle(title);
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	public Set<BookDto> findBooksByAuthor(String authorName) {
		Author author = authorRepository.findById(authorName).orElseThrow(EntityNotFoundException::new);		
		return bookRepository.findBooksByAuthorsContains(author).stream()
				.map(b -> modelMapper.map(b, BookDto.class))
				.collect(Collectors.toSet());

	}

	@Override
	public Set<BookDto> findBooksByPublisher(String publisher) {
		Publisher publisher3 = publisherRepository.findById(publisher).orElseThrow(EntityNotFoundException::new);
		return bookRepository.findBooksByPublisher(publisher3).stream()
			.map(b -> modelMapper.map(b, BookDto.class))
			.collect(Collectors.toSet());

	}

	@Override
	public Set<AuthorDto> findBookAuthors(String isbn) {
		return bookRepository.findAuthorsByIsbn(isbn).stream()
				.map(a -> modelMapper.map(a, AuthorDto.class))
				.collect(Collectors.toSet());
	}

	@Override
	public Set<String> findPublishersByAuthor(String authorName) {
		return findBooksByAuthor(authorName).stream()
			.map(b -> b.getPublisher())
			.distinct().collect(Collectors.toSet());
	}

	@Override
	public AuthorDto removeAuthor(String authorName) {
		findBooksByAuthor(authorName).stream()
			.forEach(b -> removeBook(b.getIsbn()));	
		Author author = authorRepository.findById(authorName).orElseThrow(EntityNotFoundException::new);
		authorRepository.deleteById(authorName);
		return modelMapper.map(author, AuthorDto.class);
	}



}
