package dicegame.api.services;


import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServices {
    UserDetailsService userDetailsService();
}