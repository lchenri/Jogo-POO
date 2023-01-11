//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import static utilz.Constants.GameConstants.*;

//Classe que renderiza/desenha o jogo

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private Game game;

    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this); //Objeto que captura os inputs do mouse
        this.game = game;
        setWindowSize();
        //Escutadores (teclado)
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }  

    //Define o tamanho da tela
    private void setWindowSize() {
        Dimension size = new Dimension (GAME_WIDTH,GAME_HEIGHT);
        setPreferredSize(size);
    }
    
    //Desenha
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }
    
    public Game getGame() {
        return game;
    }
}
