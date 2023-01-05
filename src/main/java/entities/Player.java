//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.PlayerConstants.getSpriteAmount;

public class Player extends Entity {
    
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 30;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;
    private float playerSpeed = 2.0f;
    
    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }
    
    public void update() {
        updatePos(); //move
        updateAnimationTick(); //Contador para possiblitar a execucao da animacao
        setAnimation(); //define a animacao que sera feita conforme a verificacao se esta movendo ou nao
    }
    
    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int)x, (int)y, 256, 160, null); //desenha personagem
    }

    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= getSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false; // sempre reseta o ataque caso o movimento finalize
            }
        }
    }

    private void setAnimation() {
        int startAni = playerAction;
        
        if(moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
        
        if(attacking)
            playerAction = ATTACK_1;
        
        if(startAni != playerAction) {
            resetAniTick(); //evita ficar "flicando" a imagem
        }
    }
    
    public void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {
        
        moving = false;
        
        if(left && !right) {
            x -= playerSpeed;
            moving = true;
        } else if(right && !left) {
            x += playerSpeed;
            moving = true;
        }
        
        if(up && !down) {
            y -= playerSpeed;
            moving = true;
        } else if(down && !up) {
            y += playerSpeed;
            moving = true;
        }
    }
    
    private void loadAnimations() {
        
        File file = new File("res/player_sprites.png");
        System.out.println(file);
        try {
            //importa o arquivo de sprites do personagem
            BufferedImage img = ImageIO.read(file);
            animations = new BufferedImage[9][6];
        
            //Divide em uma matriz todos os diferentes sprites
            for(int i = 0; i < animations.length; i++)
                for(int j = 0; j < animations[i].length; j++) {
                    animations[i][j] = img.getSubimage(j*64, i*40, 64, 40);
                }
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public boolean getLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean getUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }
    
    public boolean getRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean getDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }
    
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }
    
}
