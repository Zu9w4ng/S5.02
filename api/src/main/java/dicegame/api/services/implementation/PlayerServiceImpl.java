package dicegame.api.services.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import dicegame.api.dao.PlayerDTO;
import dicegame.api.entities.Player;
import dicegame.api.entities.PlayerGames;
import dicegame.api.exceptions.PlayerAlreadyExistException;
import dicegame.api.exceptions.PlayerNotFoundException;
import dicegame.api.repository.GameRepository;
import dicegame.api.repository.PlayerRepository;
import dicegame.api.services.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {

    GameServiceImpl gameService;

    GameRepository gameRepository;
    PlayerRepository playerRepository;

    @Override
    public PlayerDTO playerToDTO(Player player) {
        
        String name = player.getName();
        int numberOfGames = gameService.getNumberOfGames(player.getId());
        int numberOfWins = gameService.getNumberOfWins(player.getId());
        Double winRate = gameService.getWinRate(player.getId());

        return new PlayerDTO(name, numberOfGames, numberOfWins, winRate);
    }

    @Override
    public void addPlayer(String name) {

        Player player = playerRepository.findByName(name);
        if (player != null)
            throw new PlayerAlreadyExistException("This player already exists");
        
        Player newPlayer = new Player(name);
        playerRepository.save(newPlayer);
        gameRepository.save(new PlayerGames(newPlayer.getId()));
    }

    @Override
    public void updatePlayer(Long id, String newName) {
        
        if (id == null) 
            throw new IllegalArgumentException("Player ID cannot be null");

        Player existingPlayer = playerRepository.findById(id)
            .orElseThrow(() -> new PlayerNotFoundException("Player doesn't exist with id: " + id));
        existingPlayer.setName(newName);
        playerRepository.save(existingPlayer);
    }

    @Override
    public void deletePlayer(Long id) {
        
        if (id == null) 
            throw new IllegalArgumentException("Player ID cannot be null");

        Player existingPlayer = playerRepository.findById(id)
            .orElseThrow(() -> new PlayerNotFoundException("Player doesn't exist with id: " + id));
        playerRepository.delete(existingPlayer);
        gameRepository.deleteByPlayerId(id);
    }

    @Override
    public List<PlayerDTO> getAllPlayers() {
         
        return playerRepository.findAll().stream().map(p -> playerToDTO(p)).toList();
    }

    @Override
    public PlayerDTO getUnluckyPlayer() {
        
        Long playerId = gameRepository.findAll().stream().filter(p -> p.getGames().size()>0)
            .min((a,b) -> (int)(gameService.getWinRate(a.getPlayerId()) - gameService.getWinRate(b.getPlayerId())))
            .get().getPlayerId();
        
        return playerToDTO(playerRepository.findById(playerId)
            .orElseThrow(() -> new PlayerNotFoundException("Player doesn't exist with id: " + playerId)));
    }

    @Override
    public PlayerDTO getLuckyPlayer() {
        
        Long playerId = gameRepository.findAll().stream().filter(p -> p.getGames().size()>0)
            .max((a,b) -> (int)(gameService.getWinRate(a.getPlayerId()) - gameService.getWinRate(b.getPlayerId())))
            .get().getPlayerId();
        
        return playerToDTO(playerRepository.findById(playerId)
            .orElseThrow(() -> new PlayerNotFoundException("Player doesn't exist with id: " + playerId)));
    }
    
}
