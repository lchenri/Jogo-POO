/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
