package dicegame.api.services;

import java.util.List;


import dicegame.api.dao.GameDTO;
import dicegame.api.entities.Game;

public interface GameService {

    GameDTO gameToDTO(Game game);

    int getNumberOfGames(long playerId);

    int getNumberOfWins(long playerId);

    Double getWinRate(long playerId);

    GameDTO playGame(long id);

    void deleteGames(long id);

    List<GameDTO> getGames(long id);

    Double getMeanWinRate();
    
}
