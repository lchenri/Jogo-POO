//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)
/*
Vai renderizar o que vai aparecer na GameWindow
 */
package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs; // usamos esse método para colocar mouseInputs nos dois Listeners
    private float xDelta = 100;
    private float yDelta = 100;
    private int frames = 0;
    private long lastCheck = 0;

    //teste
    private float xDir = 0.005f, yDir = 0.005f;
    //
    
    
    //construtor
    public GamePanel() {
        mouseInputs = new MouseInputs(); // cria o objeto mouseInputs, para não dar erro nos dois mouseListener's
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void changeXDelta(int value) {
        this.xDelta += value;
        // toda vez que apertamos algum botão, atualiza o desenho da tela
    }

    public void changeYDelta(int value) {
        this.yDelta += value;
        // toda vez que apertamos algum botão, atualiza o desenho da tela
    }


    // ambos os métodos anteriores serão utilizados para aumentar ou decrementar a posição do quadrado na tela
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //apenas para teste
        //updateRectange();
        g.setColor(new Color(150,20,90, 255));
        //
        
        g.fillRect((int) xDelta, (int)yDelta, 50, 50);

        



    } // Devido a algumas limitações, esse método nos permite desenhar dentro do JFrame

    public void exit() {
        System.exit(0);
    }

    private void updateRectange() {
        xDelta+= xDir;
        if(xDelta > 400 || xDelta <0)
            xDir *= -1;
        
        yDelta+=yDir;
        if(yDelta > 400 || yDelta < 0)
            yDir *=-1;
    }
    
}
