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

import ie.book.domain.User;
import ie.book.requests.UserPostRequestBody;
import ie.book.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<Page<User>> list(Pageable pageable) {
		return new ResponseEntity<>(userService.listAll(pageable), HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<User> findById(@PathVariable long id) {
		return new ResponseEntity<>(userService.findByIdOrThrowBadRequestException(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<User> save(@RequestBody UserPostRequestBody userPostRequestBody) {
		return new ResponseEntity<>(userService.save(userPostRequestBody.build()), HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id) {
		userService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping
	public ResponseEntity<Void> replace(@RequestBody User user) {
		userService.replace(user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
