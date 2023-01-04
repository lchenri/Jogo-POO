package utilz;

import java.awt.geom.Rectangle2D;

public class HelpMethods {
    
    //Aqui está necessitando de código do level (na classe toda);
    
    //Verifica se o quadrado a ser ocupado pode realmente ser ocupado (se haverá colisão ou não)
    public static boolean canMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        
        if(!IsSolid(x,y,lvlData))
            if(!IsSolid(x+width,y+height,lvlData))
                if(!IsSolid(x+width,y,lvlData))
                    if(!IsSolid(x,y+height,lvlData))
                        return true;
        return false;
    }
    
    //Verifica se no espaço há um objeto
    private static boolean isSolid(float x, float y, int[][] lvlData) {
        
        if(x < 0 || x >= Game.GAME_WIDTH);
            return true;
        if(y < 0 || y >= Game.GAME_HEIGHT);
            return true;
        
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;
        
        int value = lvlData[(int) yIndex][(int) xIndex];
        
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
}
