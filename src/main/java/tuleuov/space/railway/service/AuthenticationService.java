package tuleuov.space.railway.service;

import tuleuov.space.railway.dto.JwtAuthenticationResponse;
import tuleuov.space.railway.dto.JwtAuthenticationResponseWithUserData;
import tuleuov.space.railway.dto.SignInRequest;
import tuleuov.space.railway.dto.SignUpRequest;
import tuleuov.space.railway.entity.Role;
import tuleuov.space.railway.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponseWithUserData signUp(SignUpRequest request) {

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .iin(request.getIin())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);
        var myUser = userService.getByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponseWithUserData(user.getUsername(), user.getPassword(), myUser.getEmail(),
                myUser.getPhone(), myUser.getIin(), jwt, myUser.getId());
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponseWithUserData signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());
        var myUser = userService.getByUsername(request.getUsername());
        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponseWithUserData(user.getUsername(), user.getPassword(), myUser.getEmail(),
                myUser.getPhone(), myUser.getIin(), jwt, myUser.getId());
    }
}
