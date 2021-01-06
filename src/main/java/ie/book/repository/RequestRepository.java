package ie.book.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ie.book.domain.Request;
import ie.book.enums.RequestStatusEnum;

public interface RequestRepository extends JpaRepository<Request, Long> {
	Page<Request> findByStatus(Pageable pageable, RequestStatusEnum requestStatusEnum);
}
