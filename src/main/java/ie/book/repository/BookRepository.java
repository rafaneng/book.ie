package ie.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.book.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
