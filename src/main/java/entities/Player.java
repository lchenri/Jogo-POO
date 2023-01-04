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
import static utilz.HelpMethods.canMoveHere;

public class Player extends Entity {
    
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 25;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;
    private float playerSpeed = 2.0f;
    private int[][] lvlData;
    //Precisa do codigo da parte do level
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;
    
    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, 20 * Game.SCALE, 28 * Game.SCALE);
    }
    
    public void update() {
        updatePos(); //move
        updateAnimationTick(); //Contador para possiblitar a execucao da animacao
        setAnimation(); //define a animacao que sera feita conforme a verificacao se esta movendo ou nao
    }
    
    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), width, height, null); //desenha personagem
        drawHitbox(g);
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
        if(!left && !right && !down && !up)
            return;
        
        float xSpeed = 0, ySpeed = 0;
        
        if(left && !right)
            xSpeed = -playerSpeed;
        else if(right && !left) 
            xSpeed = playerSpeed;
        
        if(up && !down) 
            y = -playerSpeed;
        else if(down && !up) 
            y = playerSpeed;
        
        if(canMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
            hitbox.y += ySpeed;
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
    
    public void loadLevelData(int[][] lvlData) {
        this.lvlData = lvlData;
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
