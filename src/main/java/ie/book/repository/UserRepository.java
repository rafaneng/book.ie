package ie.book.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.book.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

}
