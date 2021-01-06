package ie.book.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.book.domain.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByEmail(String email);

}
