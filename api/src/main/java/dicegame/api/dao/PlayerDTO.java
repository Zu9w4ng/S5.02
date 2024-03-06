package dicegame.api.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PlayerDTO {
    
    String name;
    int numberOfGames;
    int numberOfWins;
    Double winRate;
}
