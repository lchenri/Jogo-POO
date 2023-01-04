package utilz;

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
}
