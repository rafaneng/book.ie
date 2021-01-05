package ie.book.requests;

import lombok.Data;

@Data
public class CredencialsPostRequestBody {
	private String email;
	private String password;
}