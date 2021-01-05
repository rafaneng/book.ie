package ie.book.requests;

import java.time.LocalDateTime;

import ie.book.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPostRequestBody {

	private String name;
	private String email;
	private String password;
	private LocalDateTime dateCreated;

	public User build() {
		User user = new User()
				.setName(this.name)
				.setEmail(this.email)
				.setPassword(this.password)
				.setDateCreated(this.dateCreated);

		return user;
	}

}
