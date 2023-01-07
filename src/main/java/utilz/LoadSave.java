
//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;
import javax.imageio.ImageIO;
import main.Game;


public class LoadSave {
    
    public static final String PLAYER_ATLAS = "player_sprites.png"; //No lugar do name colocar o arquivo imagem JOGADOR (Ex: player_sprites.png)
    public static final String LEVEL_ATLAS = "outside_sprites.png"; //No lugar do name colocar o arquivo imagem CENARIO(Ex: outside_sprites.png)
    public static final String LEVEL_ONE_DATA = "level_one.png"; //No lugar do name colocar o arquivo imagem NIVEL(Ex: level_one.png)
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    
    //public static final String PLAYING_BG_IMG = "level_one.png"; //No lugar do name colocar o arquivo imagem FUNDO(Ex: level_one.png)
    //Na classe playing crie: BufferedImage backgroundImg;
    //No construtor de playing coloque: LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
    //No draw coloque g.drawImage(backgroundImg,0,0, Game.GAME_WIDTH, Game.GAME_HEIGHT,null);
    
    public static BufferedImage GetSpriteAtlas(String nomeArquivo) {
        
        BufferedImage img = null;
        File file = new File("res/" + nomeArquivo);
        System.out.println(file);
        try{
            //importa o arquivo de sprites do personagem
            img = ImageIO.read(file);
        }
        catch(IOException e){
            e.printStackTrace();
        } 
        return img;
    }
   
    //GetLevelData reconhece a cor apropriada para os obstaculos do nivel
    public static int [][] GetLevelData(){
        int [][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
        
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
}
