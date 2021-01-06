package ie.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ie.book.domain.Request;
import ie.book.service.RequestService;

@RestController
@RequestMapping("requests")
public class RequestController {

	@Autowired
	private RequestService requestService;

	@GetMapping(path = "/all")
	public ResponseEntity<Page<Request>> listAll(Pageable pageable) {
		return new ResponseEntity<>(requestService.listAll(pageable), HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Request> findById(@PathVariable long id) {
		return new ResponseEntity<>(requestService.findByIdOrThrowBadRequestException(id), HttpStatus.OK);
	}

	@GetMapping(path = "/pending")
	public ResponseEntity<Page<Request>> listPending(Pageable pageable) {
		return new ResponseEntity<>(requestService.listPending(pageable), HttpStatus.OK);
	}

	@GetMapping(path = "/approved")
	public ResponseEntity<Page<Request>> listApproved(Pageable pageable) {
		return new ResponseEntity<>(requestService.listApproved(pageable), HttpStatus.OK);
	}

	@PostMapping(path = "/add")
	public ResponseEntity<Request> save(@RequestBody Request request) {
		return new ResponseEntity<>(requestService.save(request), HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Request> delete(@PathVariable long id) {
		requestService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(path = "/replace")
	public ResponseEntity<Void> replace(@RequestBody Request request) {
		requestService.replace(request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping(path = "/approve/{id}")
	public ResponseEntity<Void> approveRequest(@RequestParam Long id) {
		requestService.approveRequest(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PatchMapping(path = "/reject/{id}")
	public ResponseEntity<Void> rejectRequest(@RequestParam Long id) {
		requestService.rejectRequest(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
