package ie.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.book.domain.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {

}
