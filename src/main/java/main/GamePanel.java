//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;  // No setPanelSize(classe GamePanel) colocar: Dimension size = new Dimension (GAME_WIDTH,GAME_HEIGHT) 



import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

//****Adicionou

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;  // No setPanelSize(classe GamePanel) colocar: Dimension size = new Dimension (GAME_WIDTH,GAME_HEIGHT) 

//****Adicionou

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private Game game;

    //construtor
    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;
        
        setPanelSize();

        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }
    

    private void setPanelSize() {
        //Dimension size = new Dimension(1280,800);
        Dimension size = new Dimension (GAME_WIDTH,GAME_HEIGHT);
        setPreferredSize(size);
    }
    
    public void updateGame() {
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        game.render(g);
    }
    
    public Game getGame() {
        return game;
    }

    public void exit() {
        System.exit(0);
    }
    

}
