package dicegame.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dicegame.api.entities.Player;

@Repository
public interface PlayerRepository extends JpaRepository <Player, Long> {

    Player findFirstByName(String name);
	
}