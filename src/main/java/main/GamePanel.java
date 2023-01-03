//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JPanel;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private int xDelta = 100, yDelta = 100;
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 15;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false;

    //construtor
    public GamePanel() {
        mouseInputs = new MouseInputs();
        
        importImg(); //importa o arquivo de sprites do personagem
        loadAnimations(); //Divide em uma matriz todos os diferentes sprites
        
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void loadAnimations() {
        animations = new BufferedImage[9][6];
        
        for(int i = 0; i < animations.length; i++)
            for(int j = 0; j < animations[i].length; j++) {
            animations[i][j] = img.getSubimage(j*64, i*40, 64, 40);
        }
    }
    
    private void importImg() {
        File file = new File("res/player_sprites.png");
        System.out.println(file);
        try {
            img = ImageIO.read(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    private void setPanelSize() {
        Dimension size = new Dimension(1280,800);
        setPreferredSize(size);
    }

    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;
    }
    
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= getSpriteAmount(playerAction))
                aniIndex = 0;
        }
    }

    private void setAnimation() {
        if(moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
    }

    private void updatePos() {
        
        if(moving) {
            switch(playerDir) {
                case LEFT:
                    xDelta -= 5;
                    break;
                case UP:
                    yDelta -= 5;
                    break;
                case RIGHT:
                    xDelta += 5;
                    break;
                case DOWN:
                    yDelta += 5;
                    break;
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateAnimationTick(); //Contador para possiblitar a execucao da animacao
        
        setAnimation(); //define a animacao que sera feita conforme a verificacao se esta movendo ou nao
        updatePos(); //move
        
        g.drawImage(animations[playerAction][aniIndex], xDelta, yDelta, 256, 160, null);
    }
    
    public void exit() {
        System.exit(0);
    }
}
