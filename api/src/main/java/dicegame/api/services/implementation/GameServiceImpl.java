package dicegame.api.services.implementation;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import dicegame.api.dao.GameDTO;
import dicegame.api.entities.Game;
import dicegame.api.entities.PlayerGames;
import dicegame.api.exceptions.NoGamesPlayedException;
import dicegame.api.exceptions.PlayerNotFoundException;
import dicegame.api.repository.GameRepository;
import dicegame.api.repository.PlayerRepository;
import dicegame.api.services.GameService;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;
    private PlayerRepository playerRepository;

    public GameServiceImpl(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public GameDTO gameToDTO(Game game) {
        
        int first = game.getFirst();
        int second = game.getSecond();
        boolean isWin = game.isWin();
        Date gameTime = game.getGameTime();

        return new GameDTO(first, second, isWin, gameTime);
    }

    @Override
    public int getNumberOfGames(long playerId) {
        
        return gameRepository.findByPlayerId(playerId).getGames().size();
    }

    @Override
    public int getNumberOfWins(long playerId) {
        
        return gameRepository.findByPlayerId(playerId).getGames().stream().filter(g -> g.isWin()).toList().size();
    }

    @Override
    public Double getWinRate(long playerId) {
        
        double nGames = (double) getNumberOfGames(playerId);
        if (nGames > 0)
            return ((double)getNumberOfWins(playerId))/nGames;
        return null;
    }

    @Override
    public GameDTO playGame(long id) {
        
        if(playerRepository.findById(id).isEmpty())
            throw new PlayerNotFoundException("Player doesn't exist");
			
		Game game = new Game();
        PlayerGames playerGames = gameRepository.findByPlayerId(id);
        playerGames.addGame(game);
		gameRepository.save(playerGames);
		return gameToDTO(game);
    }

    @Override
    public void deleteGames(long id) {
        
        if(playerRepository.findById(id).isEmpty())
            throw new PlayerNotFoundException("Player doesn't exist");
       
        PlayerGames playerGames = gameRepository.findByPlayerId(id);
        playerGames.getGames().clear();
        gameRepository.save(playerGames);
    }

    @Override
    public List<GameDTO> getGames(long id) {
        
        if (playerRepository.findById(id).isEmpty()) 
			throw new PlayerNotFoundException("Player doesn't exist");
        
        List<Game> games = gameRepository.findByPlayerId(id).getGames();
        return games.stream().map(g -> gameToDTO(g)).toList();
    }

    @Override
    public Double getMeanWinRate() {
        
        List<Double> means = gameRepository.findAll().stream().map(p -> getWinRate(p.getPlayerId()))
            .filter(m -> m != null).toList();
        
        if(means.size() == 0)
            throw new NoGamesPlayedException("There are no games played");

        if((double)means.size() > 0)
            return means.stream().reduce((double) 0, (a,b) -> a+b)/(double)means.size();
        
        return null;
    }
}
