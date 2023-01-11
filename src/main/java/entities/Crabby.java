//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import static utilz.Constants.GameConstants.*;
import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.canMoveHere;
import static utilz.HelpMethods.getEntityYPosUnderRoofOrAboveFloor;
import static utilz.HelpMethods.isEntityOnFloor;
import static utilz.HelpMethods.isFloor;
import static utilz.HelpMethods.isSightClear;

public class Crabby extends Entity {
    
    //AttackBox
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;
    private int aniIndex, enemyState;
    private int aniTick, aniSpeed = 25;
    private boolean firstUpdate = true;
    private boolean inAir;
    private float fallSpeed;
    private float gravity = 0.04f * SCALE;
    private float walkSpeed = 0.35f * SCALE;
    private int walkDir = LEFT;
    private int tileY;
    private float attackDistance = TILES_SIZE;
    private int maxHealth = 10;
    private int currentHealth = maxHealth;
    private boolean active = true;
    private boolean attackChecked;
    
    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT);
        initHitbox(x, y,(int) (22 * SCALE), (int)(19 * SCALE));
        initAttackBox();
    }
    
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int)(82 * SCALE), (int)(19 * SCALE));
        attackBoxOffsetX = (int)(SCALE * 30);
    }
        
    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }
    
    private void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }
    
    private void updateBehavior(int[][] lvlData, Player player) {
        if(firstUpdate)
            firstUpdateCheck(lvlData);
        
        if(inAir) {
            updateInAir(lvlData);
        } else {
            switch(enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if(canSeePlayer(lvlData, player)) {
                        turnTowardsPlayer(player);
                        if(isPlayerCloseForAttack(player))
                            newState(ATTACK);
                    }
                    move(lvlData);
                    break;
                case ATTACK:
                    if(aniIndex == 0)
                        attackChecked = false;
                    
                    if(aniIndex == 3 && !attackChecked)
                        checkEnemyHit(attackBox, player);
                    break;
                case HIT:
                    break;
            }
        }
        
    }
    
    public void drawAttackBox(Graphics g) {
        g.setColor(Color.red);
        g.drawRect((int)attackBox.x, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
    }
    
    public int flipX() {
        if(walkDir == RIGHT)
            return width;
        else
            return 0;
    }
    
    public int flipW() {
        if(walkDir == RIGHT)
            return -1;
        else
            return 1;
    }

    private void firstUpdateCheck(int[][] lvlData) {
        if(!isEntityOnFloor(hitbox, lvlData))
            inAir = true;
        firstUpdate = false;
    }
    
    private void updateInAir(int[][] lvlData) {
        if(canMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitbox.y = getEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            tileY = (int)(hitbox.y / TILES_SIZE);
        }
    }

    private void move(int[][] lvlData) {
        float xSpeed = 0;

        if(walkDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if(canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
            if(isFloor(hitbox, xSpeed, lvlData)) {
                hitbox.x += xSpeed;
                return;
            }
        changeWalkDir();
    }
    
    private void turnTowardsPlayer(Player player) {
        if(player.hitbox.x > hitbox.x)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }

    private boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int)(player.getHitbox().y / TILES_SIZE);
        if(playerTileY == tileY) {
            if(isPlayerInRange(player)) {
                if(isSightClear(lvlData, hitbox, player.hitbox, tileY))
                    return true;
            }
        }
        return false;
    }
    
    private boolean isPlayerInRange(Player player) {
        int absValue = (int)Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 5;
    }
    
    private boolean isPlayerCloseForAttack(Player player) {
        int absValue = (int)Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance;
    }

    private void newState(int enemyState) {
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }
    
    public void hurt(int amount) {
        currentHealth -= amount;
        if(currentHealth <= 0)
            newState(DEAD);
        else
            newState(HIT);
    }
    
    private void checkEnemyHit(Rectangle2D.Float attackBox, Player player) {
        if(attackBox.intersects(player.hitbox))
            player.changeHealth(-getEnemyDmg());
        attackChecked = true;
    }

    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= getSpriteAmount(enemyState)) {
                aniIndex = 0;
                
                switch(enemyState) {
                    case ATTACK,HIT -> enemyState = IDLE;
                    case DEAD -> active = false;
                }
            }
        }
    }

    //nao esta na classe crabby pois pode ser usada por outros tipos de inimigos (que nao serao implementados)
    private void changeWalkDir() {
        if(walkDir == LEFT)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }
    
    void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        active = true;
        fallSpeed = 0;
    }
    
    public int getAniIndex() {
        return aniIndex;
    }
    
    public int getEnemyState() {
        return enemyState;
    }
    
    public boolean isActive() {
        return active;
    }
    
}
