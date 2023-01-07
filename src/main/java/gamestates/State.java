/*
auxilia qualquer acao do gamestate, facilitando o acesso a alguns metodos
 */
package gamestates;

import java.awt.event.MouseEvent;
import main.Game;
import ui.MenuButton;

/**
 *
 * @author lucas
 */
public class State {

    protected Game game;

    public State(Game game) {
        this.game = game;
    }
    
    //ve se o mouse está encima 
    public boolean isIn(MouseEvent e, MenuButton mb){
        return mb.getBounds().contains(e.getX(), e.getY());
    }
    
    public Game getGame(){
        return game;
    }
}
