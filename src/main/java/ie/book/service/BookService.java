package ie.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ie.book.domain.Book;
import ie.book.exception.BadRequestException;
import ie.book.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public Page<Book> listAll(Pageable pageable) {
		return bookRepository.findAll(pageable);
	}

	@Transactional
	public Book save(Book book) {
		return bookRepository.save(book);
	}

	public Book findByIdOrThrowBadRequestException(long id) {
		return bookRepository.findById(id).orElseThrow(() -> new BadRequestException("Book not found"));
	}

	public void delete(long id) {
		bookRepository.delete(findByIdOrThrowBadRequestException(id));
	}

	public void replace(Book book) {
		Book savedBook = findByIdOrThrowBadRequestException(book.getId());
		book.setId(savedBook.getId());
		bookRepository.save(book);
	}

}
