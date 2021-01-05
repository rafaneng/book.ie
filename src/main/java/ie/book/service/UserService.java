package ie.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ie.book.domain.User;
import ie.book.exception.BadRequestException;
import ie.book.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Page<User> listAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	public User findByIdOrThrowBadRequestException(long id) {
		return userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));
	}

	@Transactional
	public User save(User user) {
		return userRepository.save(user);
	}

	public void delete(long id) {
		userRepository.delete(findByIdOrThrowBadRequestException(id));
	}

	public void replace(User user) {
		User savedUser = findByIdOrThrowBadRequestException(user.getId());
		user.setId(savedUser.getId());
		userRepository.save(user);
	}

}
