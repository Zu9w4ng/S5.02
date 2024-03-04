package dicegame.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import dicegame.api.dao.GameDTO;
import dicegame.api.dao.PlayerDTO;
import dicegame.api.services.GameService;
import dicegame.api.services.PlayerService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GameController {
    
    @Autowired
    private final PlayerService playerService;

    @Autowired
    private final GameService gameService;
    
    @PostMapping("/addPlayer")
    public ResponseEntity<String> addPlayer(@RequestBody String name) {
        
        playerService.addPlayer(name);
        return new ResponseEntity<>("Player " + name + " added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/updatePlayer/{id}")
	public ResponseEntity<String> updatePlayer(@PathVariable("id") Long id, @RequestBody String newName) {

		playerService.updatePlayer(id, newName);
		return new ResponseEntity<>("Player updated successfully", HttpStatus.OK);
	}
    
    @PostMapping("/playGame/{id}")
	public ResponseEntity<GameDTO> play(@PathVariable("id") Long id) {

		GameDTO gameDTO = gameService.playGame(id);
		return new ResponseEntity<>(gameDTO, HttpStatus.CREATED);
	}

    @DeleteMapping("/deletePlayerGames/{id}")
	public ResponseEntity<String> deletePlayerGames(@PathVariable("id") Long id) {

		gameService.deleteGames(id);
		return new ResponseEntity<>("Player games deleted successfully", HttpStatus.OK);
	}

    @DeleteMapping("/deletePlayer/{id}")
	public ResponseEntity<String> deletePlayer(@PathVariable("id") Long id) {

		playerService.deletePlayer(id);
		return new ResponseEntity<>("Player and its games deleted successfully", HttpStatus.OK);
	}

    @GetMapping("/getPlayersStats")
	public ResponseEntity<List<PlayerDTO>> getPlayersStats() {

		List<PlayerDTO> playerDTOs = playerService.getAllPlayers();
		return new ResponseEntity<>(playerDTOs, HttpStatus.OK);
	}

    @GetMapping("/getPlayerGames")
	public ResponseEntity<List<GameDTO>> getPlayerGames(@PathVariable("id") Long id) {

		List<GameDTO> playerGamesDTOs = gameService.getGames(id);
		return new ResponseEntity<>(playerGamesDTOs, HttpStatus.OK);
	}

    @GetMapping("/getMeanWinRate")
	public ResponseEntity<Double> getMeanWinRate() {

		double meanWinRate = gameService.getMeanWinRate();
		return new ResponseEntity<>(meanWinRate, HttpStatus.OK);
	}

    @GetMapping("/getUnluckyPlayer")
	public ResponseEntity<PlayerDTO> getUnluckyPlayer() {

		PlayerDTO unluckyPlayer = playerService.getUnluckyPlayer();
		return new ResponseEntity<>(unluckyPlayer, HttpStatus.OK);
	}

    @GetMapping("/getLuckyPlayer")
	public ResponseEntity<PlayerDTO> getLuckyPlayer() {
        
		PlayerDTO LuckyPlayer = playerService.getLuckyPlayer();
		return new ResponseEntity<>(LuckyPlayer, HttpStatus.OK);
	}
}
