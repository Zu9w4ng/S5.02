package dicegame.api.entities;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Game {
    
    private int first;
    private int second;
    private boolean isWin;
	private Date gameTime;

    public Game() {

        this.first = (int) Math.floor(6*Math.random()+1);
        this.second = (int) Math.floor(6*Math.random()+1);
        this.isWin = first + second == 7;
        this.gameTime = new Date(System.currentTimeMillis());
    }
    

}
