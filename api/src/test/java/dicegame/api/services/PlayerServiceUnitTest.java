package dicegame.api.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import dicegame.api.dao.PlayerDTO;
import dicegame.api.entities.Game;
import dicegame.api.entities.Player;
import dicegame.api.entities.PlayerGames;
import dicegame.api.repository.GameRepository;
import dicegame.api.repository.PlayerRepository;
import dicegame.api.services.implementation.GameServiceImpl;
import dicegame.api.services.implementation.PlayerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceUnitTest {

    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private GameRepository gameRepository;
    
    @InjectMocks
    private GameServiceImpl gameService = new GameServiceImpl(gameRepository, playerRepository);
    @InjectMocks
    private PlayerServiceImpl playerService = new PlayerServiceImpl(gameService, gameRepository, playerRepository);

    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    private PlayerGames playerGames1;
    private PlayerGames playerGames2;
    private PlayerGames playerGames3;
    private PlayerGames playerGames4;
    
    private Game game1;
    private Game game2;
    private Game game3;
    private Game game4;
    private Game game5;
    private Game game6;

    private List<Player> players = new ArrayList();
    private List<PlayerGames> playersGames = new ArrayList();

    @BeforeEach
    void setUp() {

        player1 = new Player(1, "player1", null);
        player2 = new Player(2, "player2", null);
        player3 = new Player(3, "player3", null);
        player4 = new Player(4, "player4", null);

        playerGames1 = new PlayerGames(1);
        playerGames1.setId("27d2a467a87v533d8f1616e1");

        playerGames2 = new PlayerGames(2);
        playerGames2.setId("27d2a467a87v533d8f1616e2");

        playerGames3 = new PlayerGames(3);
        playerGames3.setId("27d2a467a87v533d8f1616e3");

        playerGames1 = new PlayerGames(4);
        playerGames1.setId("27d2a467a87v533d8f1616e4");
        
        game1 = new Game();
        game1.setFirst(1);
        game1.setSecond(2);
        game1.setWin(false);

        game2 = new Game();
        game2.setFirst(2);
        game2.setSecond(5);
        game2.setWin(true);

        game3 = new Game();
        game3.setFirst(4);
        game3.setSecond(5);
        game3.setWin(false);

        game4 = new Game();
        game4.setFirst(3);
        game4.setSecond(6);
        game4.setWin(false);

        game5 = new Game();
        game5.setFirst(3);
        game5.setSecond(4);
        game5.setWin(true);

        game6 = new Game();
        game6.setFirst(5);
        game6.setSecond(2);
        game6.setWin(true);

        playerGames1.addGame(game5);
        playerGames1.addGame(game6);
        
        playerGames2.addGame(game1);
        playerGames2.addGame(game2);

        playerGames3.addGame(game3);
        playerGames3.addGame(game4);

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        
    }


    @DisplayName("Test correct PlayerDTO creation")
    @Test
    void createPlayerDTO() {

        Mockito.when(gameRepository.findByPlayerId(2)).thenReturn(playerGames2);
        PlayerDTO player2DTO = playerService.playerToDTO(player2);
        assertEquals("player2", player2DTO.getName());
        assertEquals(2, player2DTO.getNumberOfGames());
        assertEquals(1, player2DTO.getNumberOfWins());
        assertEquals(0.5, player2DTO.getWinRate());
    }

    @DisplayName("Test return player list")
    @Test
    void findAllPlayers() {

        Mockito.when(gameRepository.findByPlayerId(1)).thenReturn(playerGames1);
        Mockito.when(gameRepository.findByPlayerId(2)).thenReturn(playerGames2);
        Mockito.when(gameRepository.findByPlayerId(3)).thenReturn(playerGames3);
        Mockito.when(gameRepository.findByPlayerId(4)).thenReturn(playerGames4);
        Mockito.when(playerRepository.findAll()).thenReturn(players);
        List<PlayerDTO> playersDTOs = playerService.getAllPlayers();
        assertEquals(4, playersDTOs.size());
        verify(playerRepository).findAll();
    }
}
