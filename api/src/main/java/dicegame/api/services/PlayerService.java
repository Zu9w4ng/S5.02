package dicegame.api.services;

import java.util.List;


import dicegame.api.dao.PlayerDTO;
import dicegame.api.entities.Player;

public interface PlayerService {

    PlayerDTO playerToDTO(Player player);

    void addPlayer(String name);

    void updatePlayer(Long id, String newName);

    void deletePlayer(Long id);

    List<PlayerDTO> getAllPlayers();

    PlayerDTO getUnluckyPlayer();

    PlayerDTO getLuckyPlayer();

    
}