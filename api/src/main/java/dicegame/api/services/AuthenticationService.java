package dicegame.api.services;

import dicegame.api.dao.request.SignInRequest;
import dicegame.api.dao.request.SignUpRequest;
import dicegame.api.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
	JwtAuthenticationResponse signup(SignUpRequest request);

	JwtAuthenticationResponse signin(SignInRequest request);
}
