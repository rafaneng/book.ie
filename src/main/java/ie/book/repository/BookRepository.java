package ie.book.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ie.book.domain.Book;
import ie.book.enums.BookStatusEnum;

public interface BookRepository extends JpaRepository<Book, Long> {
	Page<Book> findByStatus(Pageable pageable, BookStatusEnum bookStatusEnum);
}
