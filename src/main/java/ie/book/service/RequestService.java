package ie.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ie.book.domain.Request;
import ie.book.enums.RequestStatusEnum;
import ie.book.exception.BadRequestException;
import ie.book.repository.RequestRepository;

@Service
public class RequestService {

	@Autowired
	private RequestRepository requestRepository;

	@Autowired
	private BookService bookService;

	public Page<Request> listAll(Pageable pageable) {
		return requestRepository.findAll(pageable);
	}
	
	public List<Request> listAllBySameBook(Long id) {
		return requestRepository.findAllByBookId(id);
	}

	public Page<Request> listPending(Pageable pageable) {
		return requestRepository.findByStatus(pageable, RequestStatusEnum.PENDING);
	}

	public Page<Request> listApproved(Pageable pageable) {
		return requestRepository.findByStatus(pageable, RequestStatusEnum.APPROVED);
	}

	public Page<Request> listRejected(Pageable pageable) {
		return requestRepository.findByStatus(pageable, RequestStatusEnum.REJECTED);
	}

	public Page<Request> listByUserId(Pageable pageable, Long id) {
		return requestRepository.findByUserId(pageable, id);
	}

	@Transactional
	public Request save(Request request) {
		return requestRepository.save(request);
	}

	public Request findByIdOrThrowBadRequestException(long id) {
		return requestRepository.findById(id).orElseThrow(() -> new BadRequestException("Request not found"));
	}

	public void delete(long id) {
		requestRepository.delete(findByIdOrThrowBadRequestException(id));
	}

	public void replace(Request request) {
		Request savedRequest = findByIdOrThrowBadRequestException(request.getId());
		request.setId(savedRequest.getId());
		requestRepository.save(request);
	}

	public void approveRequest(Long id) {

		/*
		 * Caso exista mais de um pedido para o mesmo livro, ao aprovar um pedido para
		 * um cliente, os demais devem ser automaticamente recusados.
		 */
		rejectAllRequestBySameBook(id);

		Request request = findByIdOrThrowBadRequestException(id);

		bookService.changeStatusToUnavailable(request.getBook().getId());

		request.setStatus(RequestStatusEnum.APPROVED);

		requestRepository.save(request);

	}

	public void rejectRequest(Long id) {
		Request request = findByIdOrThrowBadRequestException(id);

		request.setStatus(RequestStatusEnum.REJECTED);

		requestRepository.save(request);

	}
	
	public List<Request> rejectAllRequestBySameBook(Long id) {
		
		Request request = findByIdOrThrowBadRequestException(id);
		
		List<Request> requests = listAllBySameBook(request.getBook().getId());

		requests.remove(request);
		
		requests.forEach(requestEach -> rejectRequest(requestEach.getId()));
		
		return requests;
		
	}

}
