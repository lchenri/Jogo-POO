//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package levels;

public class Level {
    
    private int[][] lvlData;
    
    public Level(int[][] lvlData){
        this.lvlData = lvlData;
    }
    
    public int getSpriteIndex(int x, int y){
        return lvlData[y][x];
    }
}
