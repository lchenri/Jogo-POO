
//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import utilz.LoadSave;

//Essa classe eh responsavel por criar o cenario do jogo
public class LevelManager {
    
    private Game game;
    
    //Array de partes do cenario(Blocos)
    private BufferedImage[] levelSprite;
    
    private Level levelOne;
    
    public LevelManager(Game game){
        this.game = game;
       importOutsideSprites();
       levelOne = new Level(LoadSave.GetLevelData());
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
        for(int j = 0; j < Game.TILES_IN_HEIGHT; j++)
             for(int i = 0; i < Game.TILES_IN_WIDTH; i++){
                 int index = levelOne.getSpriteIndex(i, j);
                 g.drawImage(levelSprite[index],Game.TILES_SIZE*i,Game.TILES_SIZE*j,Game.TILES_SIZE,Game.TILES_SIZE,null);
             }
    }

    public void update (){
        
    }
}
