package dicegame.api.entities;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "games")
public class PlayerGames {
    
    @Id
    private String id;

    @Indexed(unique = true)
    private long playerId;

    private ArrayList<Game> games;

    public PlayerGames(long playerId) {

        this.playerId = playerId;
        this.games = new ArrayList<>();
    }

    public void addGame(Game game) {

        games.add(game);
    }
}
