package dicegame.api.dao.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

	@Email(message = "Please input a valid email format")
	private String email;
	@Size(min = 3, message = "Password must be at least 3 characters long")
	private String password;

}
