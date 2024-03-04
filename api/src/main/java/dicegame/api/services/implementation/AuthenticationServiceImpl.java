package dicegame.api.services.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dicegame.api.dao.request.SignInRequest;
import dicegame.api.dao.request.SignUpRequest;
import dicegame.api.dao.response.JwtAuthenticationResponse;
import dicegame.api.entities.User;
import dicegame.api.entities.User.Role;
import dicegame.api.exceptions.UserAlreadyExistException;
import dicegame.api.repository.UserRepository;
import dicegame.api.services.AuthenticationService;
import dicegame.api.services.JwtServices;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtServices jwtService;
	private final AuthenticationManager authenticationManager;

	@Override
	public JwtAuthenticationResponse signup(SignUpRequest request) {

		if (request.getEmail().isEmpty() || request.getPassword().isEmpty()) {
			throw new IllegalArgumentException("User email and, or password cannot be null");
		}
		
		  userRepository.findByEmail(request.getEmail())
          .ifPresent(user -> {
              throw new UserAlreadyExistException("Email is already registered:" + user.getEmail());
          });

		User user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
				.email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(User.Role.USER)
				.build();
		userRepository.save(user);
		
		String jwt = jwtService.generateToken(user);
		
		return JwtAuthenticationResponse.builder().token(jwt).build();
	}

	@Override
	public JwtAuthenticationResponse signin(SignInRequest request) {
		authenticationManager
		.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
		String jwt = jwtService.generateToken(user);
		return JwtAuthenticationResponse.builder().token(jwt).build();
	}
}