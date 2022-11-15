/*
Vai renderizar o que vai aparecer na GameWindow
 */
package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs; // usamos esse método para colocar mouseInputs nos dois Listeners
    private int xDelta = 0;
    private int yDelta = 0;

    //construtor
    public GamePanel() {
        mouseInputs = new MouseInputs(); // cria o objeto mouseInputs, para não dar erro nos dois mouseListener's
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void changeXDelta(int value) {
        this.xDelta += value;
        repaint(); // toda vez que apertamos algum botão, atualiza o desenho da tela
    }

    public void changeYDelta(int value) {
        this.yDelta += value;
        repaint(); // toda vez que apertamos algum botão, atualiza o desenho da tela
    }
    
    // ambos os métodos anteriores serão utilizados para aumentar ou decrementar a posição do quadrado na tela

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.fillRect(100 + xDelta, 100 + yDelta, 200, 50);
    } // Devido a algumas limitações, esse método nos permite desenhar dentro do JFrame
}
