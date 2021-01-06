package ie.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.book.domain.Book;
import ie.book.service.BookService;

@RestController
@RequestMapping("books")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping(path = "/all")
	public ResponseEntity<Page<Book>> listAll(Pageable pageable) {
		return new ResponseEntity<>(bookService.listAll(pageable), HttpStatus.OK);
	}

	@GetMapping(path = "/available")
	public ResponseEntity<Page<Book>> listAvailable(Pageable pageable) {
		return new ResponseEntity<>(bookService.listAvailable(pageable), HttpStatus.OK);
	}

	@GetMapping(path = "/unavailable")
	public ResponseEntity<Page<Book>> listUnavailable(Pageable pageable) {
		return new ResponseEntity<>(bookService.listUnavailable(pageable), HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Book> findById(@PathVariable long id) {
		return new ResponseEntity<>(bookService.findByIdOrThrowBadRequestException(id), HttpStatus.OK);
	}

	@PostMapping(path = "/add")
	public ResponseEntity<Book> save(@RequestBody Book book) {
		return new ResponseEntity<>(bookService.save(book), HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Book> delete(@PathVariable long id) {
		bookService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(path = "/replace")
	public ResponseEntity<Void> replace(@RequestBody Book book) {
		bookService.replace(book);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
