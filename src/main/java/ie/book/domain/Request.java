package ie.book.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ie.book.enums.RequestStatusEnum;
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
	
	@Enumerated
    @Column(columnDefinition = "int default 0", insertable = false)
	private RequestStatusEnum status;
	
	@OneToOne()
	@JoinColumn(name = "fk_id_user", nullable = false)
	private Users user;
	
	@OneToOne()
	@JoinColumn(name = "fk_id_book", nullable = false)
	private Book book;
	
	@Column(name = "date_created", nullable = false)
	private LocalDateTime dateCreated;

	@Column(name = "date_modified", nullable = true)
	private LocalDateTime dateModified;

}
