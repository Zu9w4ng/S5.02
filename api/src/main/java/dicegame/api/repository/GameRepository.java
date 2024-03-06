package dicegame.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import dicegame.api.entities.PlayerGames;


@Repository
public interface GameRepository extends MongoRepository <PlayerGames, String> {
	
	PlayerGames findByPlayerId(long playerId);

    Long deleteByPlayerId(long playerId);
	
}