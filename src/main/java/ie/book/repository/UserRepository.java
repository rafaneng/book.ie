package ie.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.book.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
