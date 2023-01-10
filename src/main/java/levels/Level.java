//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package levels;

import java.awt.image.BufferedImage;
import main.Game;
import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetCrabs;

public class Level {

    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Crabby> crabs;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    
    public Level(BufferedImage img){
        this.img = img;
        createLevelData();
        createEnenmies();
        calcLvlOffsets();
    }
    
    public int getSpriteIndex(int x, int y){
        return lvlData[y][x];
    }
    
    public int[][] getLevelData() {
        return lvlData;
    }

    private void createLevelData() {
        lvlData = GetLevelData(img);
    }

    private void createEnenmies() {
        crabs = GetCrabs(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }
    
    public int getLvlOffset(){
        return maxLvlOffsetX;
    }
    
    public ArrayList<Crabby> getCrabs(){
        return crabs;
    }
    
}
