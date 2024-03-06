package dicegame.api.services;

import java.util.List;


import dicegame.api.dao.PlayerDTO;
import dicegame.api.dao.request.PlayerRequest;
import dicegame.api.entities.Player;

public interface PlayerService {

    PlayerDTO playerToDTO(Player player);

    void addPlayer(PlayerRequest playerRequest);

    void updatePlayer(Long id, PlayerRequest playerRequest);

    void deletePlayer(Long id);

    List<PlayerDTO> getAllPlayers();

    PlayerDTO getUnluckyPlayer();

    PlayerDTO getLuckyPlayer();

    
}