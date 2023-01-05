//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)
/*
Todas as entradas do teclado
 */
package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanel;

public class KeyboardInputs implements KeyListener {

    private GamePanel gPanel;

    public KeyboardInputs(GamePanel gPanel) {
        this.gPanel = gPanel;
    } // toda vez que algum botão for apertado, usamos esse método para 
    // incrementar ou decrementar o xDelta ou yDelta dentro de GamePa

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gPanel.changeYDelta(-5); //ao pressionar a tecla 'W', o personagem é movido para cima
                break;
            case KeyEvent.VK_A:
                gPanel.changeXDelta(-5); //ao pressionar a tecla 'W', o personagem é movido para esquerda
                break;
            case KeyEvent.VK_S:
                gPanel.changeYDelta(+5); //ao pressionar a tecla 'W', o personagem é movido para baixo
                break;
            case KeyEvent.VK_D:
                gPanel.changeXDelta(+5); //ao pressionar a tecla 'W', o personagem é movido para direita
                break;
            case KeyEvent.VK_ESCAPE: //ao pressionar a tecla 'ESC', o jogo é encerrado
                gPanel.exit();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
