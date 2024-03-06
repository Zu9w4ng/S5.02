package dicegame.api.services.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import dicegame.api.dao.PlayerDTO;
import dicegame.api.dao.request.PlayerRequest;
import dicegame.api.entities.Player;
import dicegame.api.entities.PlayerGames;
import dicegame.api.exceptions.PlayerAlreadyExistException;
import dicegame.api.exceptions.PlayerNotFoundException;
import dicegame.api.repository.GameRepository;
import dicegame.api.repository.PlayerRepository;
import dicegame.api.services.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {

    private GameServiceImpl gameService;
    private GameRepository gameRepository;
    private PlayerRepository playerRepository;

    public PlayerServiceImpl(GameServiceImpl gameService, GameRepository gameRepository,
            PlayerRepository playerRepository) {
        this.gameService = gameService;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public PlayerDTO playerToDTO(Player player) {
        
        String name = player.getName();
        int numberOfGames = gameService.getNumberOfGames(player.getId());
        int numberOfWins = gameService.getNumberOfWins(player.getId());
        Double winRate = gameService.getWinRate(player.getId());

        return new PlayerDTO(name, numberOfGames, numberOfWins, winRate);
    }

    @Override
    public void addPlayer(PlayerRequest playerRequest) {

        String name = playerRequest.getName();
        Player player = playerRepository.findFirstByName(name);
        if (player != null && !(name.equals("GUEST")))
            throw new PlayerAlreadyExistException("This player already exists");
        
        Player newPlayer = new Player(name);
        playerRepository.save(newPlayer);
        gameRepository.save(new PlayerGames(newPlayer.getId()));
    }

    @Override
    public void updatePlayer(Long id, PlayerRequest playerRequest) {
        
        if (id == null) 
            throw new IllegalArgumentException("Player ID cannot be null");
        
        Player existingPlayer = playerRepository.findById(id)
            .orElseThrow(() -> new PlayerNotFoundException("Player doesn't exist with id: " + id));

        String name = playerRequest.getName();
        Player player = playerRepository.findFirstByName(name);
        if (player != null && !(name.equals("GUEST")))
            throw new PlayerAlreadyExistException("A player already exists with that name");
        
        existingPlayer.setName(name);
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
        
        List<PlayerGames> playerGames = gameRepository.findAll().stream().filter(p -> p.getGames().size()>0).toList();
        if (playerGames.size() == 0)
            throw new PlayerNotFoundException("There is no unlucky player");

        long playerId = playerGames.stream()
            .min((a,b) -> sign(gameService.getWinRate(a.getPlayerId()) - gameService.getWinRate(b.getPlayerId())))
            .get().getPlayerId();
        
        return playerToDTO(playerRepository.findById(playerId)
            .orElseThrow(() -> new PlayerNotFoundException("There is no unlucky player")));
    }

    @Override
    public PlayerDTO getLuckyPlayer() {
        
        List<PlayerGames> playerGames = gameRepository.findAll().stream().filter(p -> p.getGames().size()>0).toList();
        if (playerGames.size() == 0)
            throw new PlayerNotFoundException("There is no lucky player");

        long playerId = playerGames.stream()
            .max((a,b) -> sign(gameService.getWinRate(a.getPlayerId()) - gameService.getWinRate(b.getPlayerId())))
            .get().getPlayerId();
        
        return playerToDTO(playerRepository.findById(playerId)
            .orElseThrow(() -> new PlayerNotFoundException("There is no lucky player")));
    }

    private int sign(double d) {

        if(d<0)
            return -1;
        return 1;
    }
    
}
