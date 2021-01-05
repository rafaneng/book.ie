package ie.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import ie.book.domain.User;
import ie.book.exception.BadRequestException;
import ie.book.exception.InvalidPasswordException;
import ie.book.repository.UserRepository;
import ie.book.requests.CredencialsPostRequestBody;
import ie.book.requests.TokenPostRequestBody;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private JwtService jwtService;
    
    @Autowired
	private PasswordEncoder encoder;

	public Page<User> listAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	public User findByIdOrThrowBadRequestException(long id) {
		return userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));
	}

	public void delete(long id) {
		userRepository.delete(findByIdOrThrowBadRequestException(id));
	}

	public void replace(User user) {
		User savedUser = findByIdOrThrowBadRequestException(user.getId());
		user.setId(savedUser.getId());
		userRepository.save(user);
	}

	@Transactional
	public User save(User user) {
		String encryptedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		return userRepository.save(user);
	}

	public ResponseEntity<TokenPostRequestBody> authenticateUser(CredencialsPostRequestBody credencialsPostRequestBody) {
		try {
			User user = User.builder().email(credencialsPostRequestBody.getEmail()).password(credencialsPostRequestBody.getPassword()).build();
			authenticate(user);
			String token = jwtService.addToken(user);
			TokenPostRequestBody tokenPostRequestBody = new TokenPostRequestBody(user.getEmail(), token);
			return new ResponseEntity<TokenPostRequestBody>(tokenPostRequestBody, HttpStatus.OK);

		} catch (UsernameNotFoundException | InvalidPasswordException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

	public UserDetails authenticate(User user) {
		UserDetails userDetails = loadUserByUsername(user.getEmail());
		boolean validPassword = encoder.matches(user.getPassword(), userDetails.getPassword());

		if (validPassword) {
			return userDetails;
		}

		throw new InvalidPasswordException();
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		String[] roles = user.isAdmin() ? new String[] { "ADMIN", "USER" } : new String[] { "USER" };

		return org.springframework.security.core.userdetails.User
				.builder()
				.username(user.getEmail())
				.password(user.getPassword())
				.roles(roles).build();
	}
}
