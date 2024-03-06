package dicegame.api.dao;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GameDTO {
    
    int first;
    int second;
    boolean isWin;
    Date gameTime;
}
