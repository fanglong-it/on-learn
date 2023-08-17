package fpt.fall23.onlearn.service;
import fpt.fall23.onlearn.dto.auth.request.AuthenticationRequest;
import fpt.fall23.onlearn.dto.auth.request.GoogleLoginRequest;
import fpt.fall23.onlearn.dto.auth.response.AuthenticationResponse;
import fpt.fall23.onlearn.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface AuthenticationService {
    AuthenticationResponse authentication(AuthenticationRequest request);

    AuthenticationResponse refreshToken(String token, HttpServletRequest request);

    Optional<Account> getCurrentAuthenticatedAccount();

    Optional<String> getCurrentAuthentication();

    List<String> getCurrentAuthenticationRoles();

    boolean isAdmin();

    AuthenticationResponse googleLogin(GoogleLoginRequest request);
}
