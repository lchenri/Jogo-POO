//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package entities;

import gamestates.Playing;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
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
    private float playerSpeed = 1.0f * Game.SCALE;
    //playerSpeed = 1.0f
    private int[][] lvlData;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;

    //modifiers - DEBUG
    private static boolean gravityMod = false;
    private static float gravityMultiplier = 0.0f;
    private static boolean jumpMod = false;
    private static float jumpMultiplier = 0.0f;
    private static boolean speedMod = false;
    private static float speedMultiplier = 0.0f;
    private static boolean fallSpeedMod = false;
    private static float fallSpeedMultiplier = 0.0f;

    //Jumping / Falling
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;
    
    //StatusBarUI
    private BufferedImage statusBarImg;
    
    private int statusBarWidth = (int) (192 * Game.SCALE);
    private int statusBarHeight = (int) (58 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);
    
    private int healthBarWidth = (int) (150 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarXStart = (int) (34 * Game.SCALE);
    private int healthBarYStart = (int) (14 * Game.SCALE);
    
    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;
    
    //AttackBox
    private Rectangle2D.Float attackBox;
    
    private int flipX = 0;
    private int flipW = 1;
    
    private boolean attackChecked;
    private Playing playing;
    
    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        loadAnimations();
        initHitbox(x, y, (int) (20 * Game.SCALE), (int) (26 * Game.SCALE)); //inicializa hitbox
        initAttackBox();
    }
    
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int)(20 * Game.SCALE), (int)(20 * Game.SCALE));
    }

    public void update() {
        updateHealthBar();
        if(currentHealth <= 0) {
            playing.setGameOver(true);
            return;
        }
        
        updateAttackBox();
        updatePos(); //move
        if(attacking)
            checkAttack();
        updateAnimationTick(); //Contador para possiblitar a execucao da animacao
        setAnimation(); //define a animacao que sera feita conforme a verificacao se esta movendo ou nao
    }
    
    private void checkAttack() {
        if(attackChecked || aniIndex != 1)
            return;
        attackChecked = true;
        playing.checkEnemyHit(attackBox);
    }
    
    private void updateAttackBox() {
        if(right) {
            attackBox.x = hitbox.x + hitbox.width + (int)(Game.SCALE * 10);
        } else if(left) {
            attackBox.x = hitbox.x - hitbox.width - (int)(Game.SCALE * 10);
        }
        attackBox.y = hitbox.y + (Game.SCALE * 10);
    }
    
    private void updateHealthBar() {
        healthWidth = (int)((currentHealth / (float)(maxHealth)) * healthBarWidth);
    }
    
    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x - xDrawOffset) + flipX, (int)(hitbox.y - yDrawOffset), width * flipW, height, null); //desenha personagem conforme seu hitbox
        //drawHitbox(g); //desenha hitbox (teste)
//        drawAttackBox(g);
        drawUI(g);
    }
    
    private void drawAttackBox(Graphics g) {
        g.setColor(Color.red);
        g.drawRect((int)attackBox.x, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
    }
    
    private void drawUI(Graphics g) {
        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthBarWidth, healthBarHeight);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false; // sempre reseta o ataque caso o movimento finalize
                attackChecked = false;
            }
        }
    }

    private void setAnimation() {
        int startAni = playerAction;

        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (inAir) {
            if (airSpeed < 0) {
                playerAction = JUMP;
            } else {
                playerAction = FALLING;
            }
        }
        
        if(attacking) {
            playerAction = ATTACK;
            if(startAni != ATTACK) {
                aniIndex = 1;
                aniTick = 0;
                return;
            }
        }

        if (startAni != playerAction) {
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
        if (jump) {
            jump(); //pula
        }
        if (!left && !right && !inAir) {
            return; //se está no ar não pula
        }
        float xSpeed = 0;

        //movimentacao horizontal
        if (left) {
            if (speedMod == true) {
                xSpeed -= playerSpeed + speedMultiplier;
            } else {
                xSpeed -= playerSpeed;
            }
            flipX = width;
            flipW = -1;
        }

        if (right) {
            if(speedMod == true){
                xSpeed += playerSpeed + speedMultiplier;
            }else{
                xSpeed += playerSpeed;
            }
            flipX = 0;
            flipW = 1;
        }

        if (!inAir) {
            if (!isEntityOnFloor(hitbox, lvlData)) {
                inAir = true;
            }
        }

        //movimentacao vertical
        if (inAir) {
            if (canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                if (gravityMod == true) {
                    airSpeed += gravity + gravityMultiplier;
                } else {
                    airSpeed += gravity;
                }
                updateXPos(xSpeed);
            } else {
                hitbox.y = getEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed); //analisa se colidiu com teto ou parou no chao
                if (airSpeed > 0) {
                    resetInAir(); //nao esta mais no ar
                } else {
                    if(fallSpeedMod == true){
                        airSpeed = fallSpeedAfterCollision + fallSpeedMultiplier;
                    }else{
                        airSpeed = fallSpeedAfterCollision; //parou no ar apos colidir
                    }
                }
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }

        moving = true;

    }

    private void jump() {
        if (inAir) {
            return;
        }
        inAir = true;
        if (jumpMod == true) {
            airSpeed = jumpSpeed + jumpMultiplier;
        } else {
            airSpeed = jumpSpeed;
        }
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }
    
    public void changeHealth(int value) {
        currentHealth += value;
        if(currentHealth <= 0) {
            currentHealth = 0;
            //gameOver();
        } else if(currentHealth >= maxHealth)
            currentHealth = maxHealth;
    }

    private void updateXPos(float xSpeed) {
        if (canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = getEntityXPosNextToWall(hitbox, xSpeed); //analisa se colidiu horizontalmente
        }
    }

    private void loadAnimations() {

        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[7][8];

        //Divide em uma matriz todos os diferentes sprites
        for(int i = 0; i < animations.length; i++)
            for(int j = 0; j < animations[i].length; j++)
                animations[i][j] = img.getSubimage(j*64, i*40, 64, 40);
        
        statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
    }

    public void loadLevelData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!isEntityOnFloor(hitbox, lvlData)) //se estiver no ar, cai
        {
            inAir = true;
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

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void resetAll() {
       resetDirBooleans();
       attacking = false;
       inAir = false;
       moving = false;
       playerAction = IDLE;
       currentHealth = maxHealth;
       
       hitbox.x = x;
       hitbox.y = y;
       
       if(!isEntityOnFloor(hitbox, lvlData)) //se estiver no ar, cai
            inAir = true;
       
       resetAdminValues();
    }

    public static float getGravityMultiplier() {
        return gravityMultiplier;
    }

    public static void setGravityMultiplier(float gravityMultiplierR) {
        gravityMultiplier = gravityMultiplierR;
        System.out.println("VALOR DO MULTIPLICADOR = " + gravityMultiplierR);
        gravityMod = true;
        
        if(gravityMultiplier == 0)
            gravityMod = false;
        
        System.out.println("Status mod = " + gravityMod);
    }

    public static float getJumpMultiplier() {
        return jumpMultiplier;
    }

    public static void setJump(float jumpMultiplierR) {
        jumpMultiplier = jumpMultiplierR;
        System.out.println("Valor do Multiplicador = " + jumpMultiplierR);
        jumpMod = true;
        if(jumpMultiplier == 0)
            jumpMod = false;
        System.out.println("Status mod = " + jumpMod);
    }

    public static float getSpeedMultiplier() {
        return speedMultiplier;
    }

    public static void setSpeed(float speedMultiplierR) {
        speedMultiplier = speedMultiplierR;
        System.out.println("Valor do multiplicador = " + speedMultiplierR);
        speedMod = true;
        if(speedMultiplier == 0)
            speedMod = false;
        System.out.println("Status mod = " + speedMod);
    }
    
    public static float getFallSpeedMultiplier(){
        return fallSpeedMultiplier;
    }
    
    public static void setFallSpeedMultiplier(float fallSpeedMultiplierR){
        fallSpeedMultiplier = fallSpeedMultiplierR;
        System.out.println("Valor do multiplicador = " + fallSpeedMultiplierR);
        fallSpeedMod = true;
        if(fallSpeedMultiplier == 0)
            fallSpeedMod = false;
        System.out.println("Status mod = " + fallSpeedMod);
    }

    private void resetAdminValues() {
        gravityMod = false;
        jumpMod = false;
        speedMod = false;
        fallSpeedMod = false;
        gravityMultiplier = 0.0f;
        jumpMultiplier = 0.0f;
        speedMultiplier = 0.0f;
        fallSpeedMultiplier = 0.0f;
    }

}
