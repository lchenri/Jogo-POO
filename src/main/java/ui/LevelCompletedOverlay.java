//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Game;
import static utilz.Constants.UI.URMButtons.*;
import utilz.LoadSave;
import static utilz.Constants.GameConstants.*;

public class LevelCompletedOverlay {
    
    private Playing playing;
    private UrmButton menu, next; //Variaveis responsaveis pelos botoes de proximo nivel quando o overlay de nivel completo aparecer
    private BufferedImage img;
    private int bgX, bgY, bgW, bgH; // Variaveis que decidem o quao grande sera a imagem do nivel
    

    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
        initImg();
        initButtons();
    }

    //metodo responsavel por identificar qual a imagem de pressao dos botoes
    private void initButtons() {
        int menuX = (int) (330 * SCALE);
        int nextX = (int) (445 * SCALE);
        int y = (int) (195* SCALE);
        next = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton(menuX, y,URM_SIZE, URM_SIZE, 2);
    }
    
    //metodo responsavel por inicializar um nivel com suas proporcoes
    private void initImg() {
       img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMG);
       bgW = (int) (img.getWidth() * SCALE);
       bgH = (int) (img.getHeight()* SCALE);
       bgX = GAME_WIDTH / 2 - bgW / 2;
       bgY = (int) (75 * SCALE);
    }

    public void draw(Graphics g){
        g.drawImage(img, bgX, bgY, bgW, bgH, null);
        next.draw(g);
        menu.draw(g);
    }
    
    public void update(){
        next.update();
        menu.update();
    }
    
    //metodo responsavel por checar se o mouse esta no botao para que ele mova
    private boolean isIn(UrmButton b, MouseEvent e){
        return b.getBounds().contains(e.getX(), e.getY());
    }
    
    public void mouseMoved(MouseEvent e){
        next.setMouseOver(false);
        menu.setMouseOver(false);
        
        if(isIn(menu, e))
            menu.setMouseOver(true);
        else if (isIn(next,e))
            next.setMouseOver(true);
    }
    public void mouseReleased(MouseEvent e){
        
        if(isIn(menu, e)) {
            
            if(menu.isMousePressed()){
                playing.resetAll();
                Gamestate.state = Gamestate.MENU;
            }           
        }else if (isIn(next,e))
            if(next.isMousePressed()){
                playing.loadNextLevel();
            }
        
        menu.resetBools();
        next.resetBools();
    }
    
    public void mousePressed(MouseEvent e){
        
        if(isIn(menu, e))
            menu.setMousePressed(true);
        else if (isIn(next,e))
            next.setMousePressed(true);
    }
    
}
