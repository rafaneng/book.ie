package ie.book.service;

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

	public Page<Request> listPending(Pageable pageable) {
		return requestRepository.findByStatus(pageable, RequestStatusEnum.PENDING);
	}

	public Page<Request> listApproved(Pageable pageable) {
		return requestRepository.findByStatus(pageable, RequestStatusEnum.APPROVED);
	}

	public Page<Request> listRejected(Pageable pageable) {
		return requestRepository.findByStatus(pageable, RequestStatusEnum.REJECTED);
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
		Request savedRequest = findByIdOrThrowBadRequestException(id);

		bookService.changeStatusToUnavailable(savedRequest.getBook().getId());

		savedRequest.setStatus(RequestStatusEnum.APPROVED);

		requestRepository.save(savedRequest);

	}

	public void rejectRequest(Long id) {
		Request savedRequest = findByIdOrThrowBadRequestException(id);

		savedRequest.setStatus(RequestStatusEnum.REJECTED);

		requestRepository.save(savedRequest);

	}

}
