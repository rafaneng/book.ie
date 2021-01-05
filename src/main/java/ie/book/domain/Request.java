package ie.book.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "request")
public class Request {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Boolean status;
	
	@OneToOne()
	@JoinColumn(name = "fk_id_user", nullable = false)
	private User user;
	
	@OneToOne()
	@JoinColumn(name = "fk_id_book", nullable = false)
	private Book book;
	
	@Column(name = "date_created", nullable = false)
	private LocalDateTime dateCreated;

	@Column(name = "date_modified", nullable = true)
	private LocalDateTime dateModified;

}
