/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import main.Game;

/**
 *
 * @author pedro
 */
public class LoadSave {
    
    public static final String PLAYER_ATLAS = name; //No lugar do name colocar o arquivo imagem JOGADOR (Ex: player_sprites.png)
    public static final String LEVEL_ATLAS = name; //No lugar do name colocar o arquivo imagem CENARIO(Ex: outside_sprites.png)
    public static final String LEVEL_ONE_DATA = name; //No lugar do name colocar o arquivo imagem NIVEL(Ex: level_one.png)
    public static final String PLAYING_BG_IMG = name; //No lugar do name colocar o arquivo imagem FUNDO(Ex: level_one.png)
    //Na classe playing crie: BufferedImage backgroundImg;
    //No construtor de playing coloque: LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
    //No draw coloque g.draImage(backgroundImg,0,0, Game.GAME_WIDTH, Game.GAME_HEIGHT,null);
    
    public static BufferedImage GetSpriteAtlas(String nomeArquivo){
        
        //A verificacao de excecao na funcao loadAnimations(classe player) foi passada pra ca.
        // Na classe player colocar BufferedImage img = LoadSave.GetSpriteAtlas(loadSave.PLAYER_ATLAS);
        
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + nomeArquivo);
        try{
            img = ImageIO.read(is);
        }
        catch(IOException e){
            e.printStackTrace();
        } finally {
            try{
                is.close();
            } catch(IOException e){
                e.printStackTrace();
            }
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
