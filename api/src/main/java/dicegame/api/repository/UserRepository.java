package dicegame.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dicegame.api.entities.User;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository <User, Integer> {
    
    Optional<User> findByEmail(String email);
}
