package de.amin.gamestates;

import de.amin.hardcoregames.HG;
import de.amin.kit.KitManager;
import de.amin.managers.ItemManager;

public class GameStateManager {

    private HG plugin;
    private GameState[] gameStates;
    private GameState currentGameState;

    public GameStateManager(HG plugin, KitManager kitManager, ItemManager itemManager){
        this.plugin = plugin;
        gameStates = new GameState[4];

        gameStates[GameState.LOBBY_STATE] = new LobbyState(this);
        gameStates[GameState.INVINCIBILITY_STATE] = new InvincibilityState(this, kitManager);
        gameStates[GameState.INGAME_STATE] = new IngameState(itemManager);
        gameStates[GameState.ENDING_STATE] = new EndingState();
    }

    public void setGameState(int gameStateID){
        if(currentGameState != null)
            currentGameState.stop();
        currentGameState=gameStates[gameStateID];
        currentGameState.start();

    }

    public void stopCurrentGameState(){
        if(currentGameState!=null){
            currentGameState.stop();
            currentGameState = null;
        }
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public GameState[] getGameStates() {
        return gameStates;
    }

    public LobbyState getLobbyState(){
        return (LobbyState) gameStates[GameState.LOBBY_STATE];
    }

    public InvincibilityState getInvincibilityState(){
        return (InvincibilityState) gameStates[GameState.INVINCIBILITY_STATE];
    }

    public IngameState getIngameState(){
        return (IngameState) gameStates[GameState.INGAME_STATE];
    }

    public EndingState getEndingState(){
        return (EndingState) gameStates[GameState.ENDING_STATE];
    }
}
