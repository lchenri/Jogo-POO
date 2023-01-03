//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanel;

public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.changeYDelta(-5); //ao pressionar a tecla 'W', o personagem é movido para cima
                break;
            case KeyEvent.VK_A:
                gamePanel.changeXDelta(-5); //ao pressionar a tecla 'A', o personagem é movido para esquerda
                break;
            case KeyEvent.VK_S:
                gamePanel.changeYDelta(+5); //ao pressionar a tecla 'S', o personagem é movido para baixo
                break;
            case KeyEvent.VK_D:
                gamePanel.changeXDelta(+5); //ao pressionar a tecla 'D', o personagem é movido para direita
                break;
            case KeyEvent.VK_ESCAPE: //ao pressionar a tecla 'ESC', o jogo é encerrado
                gamePanel.exit();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
