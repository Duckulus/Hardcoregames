package de.amin.gamestates;

public abstract class GameState {

    public static final int LOBBY_STATE = 0,
                            INVINCIBILITY_STATE = 1,
                            INGAME_STATE = 2,
                            ENDING_STATE = 3;

    public abstract void start();
    public abstract void stop();

}
