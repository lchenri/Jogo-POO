//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.Game;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.PlayerConstants.getSpriteAmount;
import static utilz.HelpMethods.*;
import utilz.LoadSave;

public class Player extends Entity {
    
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 25;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down, jump;
    private float playerSpeed = 2.0f;
    private int[][] lvlData;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;
    
    //Jumping / Falling
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    
    private boolean inAir = false;
    
    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, 20 * Game.SCALE, 26 * Game.SCALE); //inicializa hitbox
    }
    
    public void update() {
        updatePos(); //move
        updateAnimationTick(); //Contador para possiblitar a execucao da animacao
        setAnimation(); //define a animacao que sera feita conforme a verificacao se esta movendo ou nao
    }
    
    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), width, height, null); //desenha personagem conforme seu hitbox
        //drawHitbox(g); //desenha hitbox (teste)
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
        
        if(inAir) {
            if(airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = FALLING;
        }
        
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
        //movimentacao pulo
        if(jump)
            jump(); //pula
        if(!left && !right && !inAir)
            return; //se está no ar não pula
        
        float xSpeed = 0;
        
        //movimentacao horizontal
        if(left)
            xSpeed -= playerSpeed;
        
        if(right) 
            xSpeed += playerSpeed;
        
        if(!inAir)
            if(!isEntityOnFloor(hitbox, lvlData))
                inAir = true;
        
        //movimentacao vertical
        if(inAir) {
            if(canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = getEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed); //analisa se colidiu com teto ou parou no chao
                if(airSpeed > 0)
                    resetInAir(); //nao esta mais no ar
                else
                    airSpeed = fallSpeedAfterCollision; //parou no ar apos colidir
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }
        
        moving = true;
        
    }
    
    private void jump() {
        if(inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }
    
    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if(canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = getEntityXPosNextToWall(hitbox, xSpeed); //analisa se colidiu horizontalmente
        }
    }
    
    private void loadAnimations() {
        
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[9][6];

        //Divide em uma matriz todos os diferentes sprites
        for(int i = 0; i < animations.length; i++)
            for(int j = 0; j < animations[i].length; j++)
                animations[i][j] = img.getSubimage(j*64, i*40, 64, 40);

    }
    
    public void loadLevelData(int[][] lvlData) {
        this.lvlData = lvlData;
        if(!isEntityOnFloor(hitbox, lvlData)) //se estiver no ar, cai
            inAir = true;
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
    
    public void setJump(boolean jump) {
        this.jump = jump;
    }
    
}
