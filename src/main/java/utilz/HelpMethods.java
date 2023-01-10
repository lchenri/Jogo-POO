package utilz;

import entities.Crabby;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.Game;
import static utilz.Constants.EnemyConstants.CRABBY;
import static utilz.LoadSave.GetSpriteAtlas;

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
        
        if(x < 0 || x >= Game.GAME_WIDTH)
            return true;
        if(y < 0 || y >= Game.GAME_HEIGHT)
            return true;
        
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;
        
        return isTileSolid((int)xIndex, (int)yIndex, lvlData);
    }
    
    public static boolean isTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];
        
        if(value >= 48 || value < 0 || value != 11)
            return true;
        return false;        
    }
    
    public static float getEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        
        int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
        
        if(xSpeed > 0) {
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset -1;
        } else {
            return currentTile * Game.TILES_SIZE;
        }
    }
    
    public static float getEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox,float airSpeed) {
        int currentTile = (int)(hitbox.y / Game.TILES_SIZE);
        if(airSpeed > 0) {
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int)(Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else {
            return currentTile * Game.TILES_SIZE;
        }
            
    }
    
    public static boolean isEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        if(!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if(!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;
        return true;
    }
    
    public static int [][] GetLevelData(BufferedImage img){
        int [][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        
        //img.getWidth retornando 50, e getHeight retornando 14, sendo que o tamanho do array de lvlData é [14][26]
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
        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);
        
        if(firstXTile > secondXTile)
            isAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
        else
            isAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
        
        return false;
    }
    
    public static ArrayList<Crabby> getCrabs(BufferedImage img) {
        ArrayList<Crabby> list = new ArrayList<>();
        for(int j = 0; j < img.getHeight(); j++)
            for(int i = 0; i < img.getWidth(); i++) {
                Color color = new Color (img.getRGB(i, j));
                int value = color.getGreen();
                
                if(value == CRABBY)
                    list.add(new Crabby(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
            }
        return list;
    }
}
