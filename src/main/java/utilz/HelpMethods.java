//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package utilz;

import entities.Crabby;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static utilz.Constants.EnemyConstants.ENEMY;
import static utilz.Constants.GameConstants.*;

public class HelpMethods {
    
    //Verifica se o quadrado a ser ocupado pode realmente ser ocupado (se haverá colisão ou não)
    public static boolean canMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        
        if(!isSolid(x,y,lvlData))
            if(!isSolid(x+width,y+height,lvlData))
                if(!isSolid(x+width,y,lvlData))
                    if(!isSolid(x,y+height,lvlData))
                        return true;
        return false;
    }
    
    //Verifica se no espaço há um objeto
    private static boolean isSolid(float x, float y, int[][] lvlData) {
        
        if(x < 0 || x >= GAME_WIDTH)
            return true;
        if(y < 0 || y >= GAME_HEIGHT)
            return true;
        
        float xIndex = x / TILES_SIZE;
        float yIndex = y / TILES_SIZE;
        
        return isTileSolid((int)xIndex, (int)yIndex, lvlData);
    }
    
    public static boolean isTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];
        
        if(value >= 48 || value < 0 || value != 11)
            return true;
        return false;        
    }
    
    public static float getEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        
        int currentTile = (int)(hitbox.x / TILES_SIZE);
        
        if(xSpeed > 0) {
            int tileXPos = currentTile * TILES_SIZE;
            int xOffset = (int)(TILES_SIZE - hitbox.width);
            return tileXPos + xOffset -1;
        } else {
            return currentTile * TILES_SIZE;
        }
    }
    
    public static float getEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox,float airSpeed) {
        int currentTile = (int)(hitbox.y / TILES_SIZE);
        if(airSpeed > 0) {
            int tileYPos = currentTile * TILES_SIZE;
            int yOffset = (int)(TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else {
            return currentTile * TILES_SIZE;
        }
            
    }
    
    public static boolean isEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        if(!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if(!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;
        return true;
    }
    
    public static int [][] GetLevelData(BufferedImage img){
        int [][] lvlData = new int[TILES_IN_HEIGHT][TILES_IN_WIDTH];
        
        for(int j = 0; j < img.getHeight(); j++)
            for(int i = 0; i < img.getWidth(); i++) {
                Color color = new Color (img.getRGB(i, j));
                int value = color.getRed();
                
                if(value >= 48)
                    value = 0;
                lvlData[j][i] = value;
            }
        
        return lvlData;
        
    }
    
    public static boolean isFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        if(xSpeed > 0)
            return isSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        return isSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
    }
    
    public static boolean isAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        for(int i = 0; i < xEnd - xStart; i++) {
            if(isTileSolid(xStart + i, y, lvlData))
                return false;
            if(!isTileSolid(xStart + i, y+1, lvlData))
                return false;
        }
        return true;
    }
    
    //este metodo poderia estar apenas no inimigo, mas pode ser usado em outras classes, como player, algum tipo novo de inimigo, etc. (Line of Sight)
    public static boolean isSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
        int firstXTile = (int) (firstHitbox.x / TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / TILES_SIZE);
        
        if(firstXTile > secondXTile) {
            return isAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
        } else {
            return isAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
        }
    }
    
    public static ArrayList<Crabby> getCrabs(BufferedImage img) {
        ArrayList<Crabby> list = new ArrayList<>();
        for(int j = 0; j < img.getHeight(); j++)
            for(int i = 0; i < img.getWidth(); i++) {
                Color color = new Color (img.getRGB(i, j));
                int value = color.getGreen();
                
                if(value == ENEMY)
                    list.add(new Crabby(i * TILES_SIZE, j * TILES_SIZE));
            }
        return list;
    }
}
