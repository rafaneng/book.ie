package ie.book.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import ie.book.enums.BookStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated
    @Column(columnDefinition = "int default 0", insertable = false)
	private BookStatusEnum status;
	
	@Column(length = 150, nullable = false)
	private String name;
	
	@Column(length = 100, nullable = true)
	private String author;
	
	@Column(name = "date_created", nullable = false)
	private LocalDateTime dateCreated;

	@Column(name = "date_modified", nullable = true)
	private LocalDateTime dateModified;

}
