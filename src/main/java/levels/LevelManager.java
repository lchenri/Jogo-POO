//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package levels;

import gamestates.Gamestate;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.GameConstants.*;

//Essa classe eh responsavel por criar o cenario do jogo
public class LevelManager {
    
    private Game game;
    
    //Array de partes do cenario(Blocos)
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int lvlIndex = 0;
    
    public LevelManager(Game game){
        this.game = game;
       importOutsideSprites();
       levels = new ArrayList<>();
       buildAllLevels();
    }
    public void loadNextLevel(){
        lvlIndex++;
        if(lvlIndex >= levels.size()){
            lvlIndex =0;
            System.out.println("Sem mais niveis! Jogo completo");
            Gamestate.state = Gamestate.MENU;
        }
        Level newLevel = levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLevelData(newLevel.getLevelData());
        game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
    }
    
    private void buildAllLevels(){
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for(BufferedImage img : allLevels)
            levels.add(new Level(img));
    }
    
    private void importOutsideSprites(){
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        
        //separando as partes de cada bloco do cenario
        levelSprite = new BufferedImage[48]; // dimensao da imagem do cenario eh 4 de altura e 12 de largura, entao 12x4 = 48
        for(int j = 0; j < 4; j++)
            for(int i = 0; i < 12; i++){
                int index = j*12 + i;
                levelSprite[index] = img.getSubimage(i*32, j*32, 32, 32);
            }
    }
    
    public void draw(Graphics g) {
        //pegando uma parte da imagem inteira(imagem cenario)
        for(int j = 0; j < TILES_IN_HEIGHT; j++)
             for(int i = 0; i < TILES_IN_WIDTH; i++){
                 int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                 g.drawImage(levelSprite[index],TILES_SIZE*i,TILES_SIZE*j,TILES_SIZE,TILES_SIZE,null);
             }
    }

    public void update (){
        
    }
    
    public Level getCurrentLevel() {
        return levels.get(lvlIndex);
    }
    
    public int getAmountOfLvels(){
        return levels.size();
    }
}
