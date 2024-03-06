package dicegame.api.exceptions;

public class NoGamesPlayedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NoGamesPlayedException(String msg) {
        super(msg);
    }
}
