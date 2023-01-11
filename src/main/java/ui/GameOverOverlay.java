//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import static utilz.Constants.GameConstants.*;

public class GameOverOverlay {
    
    private Playing playing;
    
    public GameOverOverlay(Playing playing) {
        this.playing = playing;
    }
    
    public void draw(Graphics g) {
        g.setColor(new Color(0,0,0,255));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        g.setColor(Color.white);
        g.drawString("Game Over", GAME_WIDTH / 2, GAME_HEIGHT / 2);
        g.drawString("Press esc to enter Main Menu!", GAME_WIDTH / 2, (GAME_HEIGHT / 2) + 50);
    }
    
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }
}
