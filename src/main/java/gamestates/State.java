/*
auxilia qualquer acao do gamestate, facilitando o acesso a alguns metodos
 */
package gamestates;

import main.Game;

/**
 *
 * @author lucas
 */
public class State {

    protected Game game;

    public State(Game game) {
        this.game = game;
    }
    
    public Game getGame(){
        return game;
    }
}
