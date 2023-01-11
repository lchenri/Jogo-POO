//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package entities;

import java.awt.geom.Rectangle2D;
import main.Game;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;

public abstract class Enemy extends Entity {
    
    protected int aniIndex, enemyState, enemyType;
    protected int aniTick, aniSpeed = 25;
    protected boolean firstUpdate = true;
    protected boolean inAir;
    protected float fallSpeed;
    protected float gravity = 0.04f * Game.SCALE;
    protected float walkSpeed = 0.35f * Game.SCALE;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;
    protected int maxHealth;
    protected int currentHealth;
    protected boolean active = true;
    protected boolean attackChecked;
    
    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
        maxHealth = getMaxHealth(enemyType);
        currentHealth = maxHealth;
    }
    
    //pode ser usado em outras classes de inimigos
    protected void firstUpdateCheck(int[][] lvlData) {
        if(!isEntityOnFloor(hitbox, lvlData))
            inAir = true;
        firstUpdate = false;
    }
    
    //pode ser usado em outras classes de inimigos
    protected void updateInAir(int[][] lvlData) {
        if(canMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitbox.y = getEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            tileY = (int)(hitbox.y / Game.TILES_SIZE);
        }
    }
    
    //pode ser usado em outras classes de inimigos
    protected void move(int[][] lvlData) {
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
    
    protected void turnTowardsPlayer(Player player) {
        if(player.hitbox.x > hitbox.x)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }
    
    //pode ser usado em outras classes de inimigos
    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int)(player.getHitbox().y / Game.TILES_SIZE);
        if(playerTileY == tileY) {
            if(isPlayerInRange(player)) {
                if(isSightClear(lvlData, hitbox, player.hitbox, tileY))
                    return true;
            }
        }
        return false;
    }
    
    protected boolean isPlayerInRange(Player player) {
        int absValue = (int)Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 5;
    }
    
    protected boolean isPlayerCloseForAttack(Player player) {
        int absValue = (int)Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance;
    }
    
    //pode ser usado em outras classes de inimigos
    protected void newState(int enemyState) {
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
    
    protected void checkEnemyHit(Rectangle2D.Float attackBox, Player player) {
        if(attackBox.intersects(player.hitbox))
            player.changeHealth(-getEnemyDmg(enemyType));
        attackChecked = true;
    }
    
    //nao esta na classe crabby pois pode ser usada por outros tipos de inimigos (que nao serao implementados)
    protected void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= getSpriteAmount(enemyType, enemyState)) {
                aniIndex = 0;
                
                switch(enemyState) {
                    case ATTACK,HIT -> enemyState = IDLE;
                    case DEAD -> active = false;
                }
            }
        }
    }

    //nao esta na classe crabby pois pode ser usada por outros tipos de inimigos (que nao serao implementados)
    protected void changeWalkDir() {
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
