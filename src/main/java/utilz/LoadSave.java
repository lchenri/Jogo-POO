
//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Game;


public class LoadSave {
    
    public static final String PLAYER_ATLAS = "player_sprites.png"; //No lugar do name colocar o arquivo imagem JOGADOR (Ex: player_sprites.png)
    public static final String LEVEL_ATLAS = "outside_sprites.png"; //No lugar do name colocar o arquivo imagem CENARIO(Ex: outside_sprites.png)
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String PLAYING_BG_IMG = "level_one.png"; // ADICIONAR ARQUIVO DO BCKGROUND AQUI
    public static final String COMPLETED_IMG = "completed_sprite.png";// ADICIONAR ARQUIVO DE COMPLETED
    
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
    
    public static BufferedImage[] GetAllLevels(){
        
        //importando o pacote lvls com suas devidas excecoes evitando erro
        URL url = LoadSave.class.getResource("/lvls");
        File file = null;
        
       try{
           file = new File(url.toURI());
       } catch (URISyntaxException e){
           e.printStackTrace();
       }
       
       File[] files = file.listFiles();
       File[] filesSorted = new File[files.length];
       
       //Sorteia qual nivel sera executado no jogo de forma que os niveis ficam ordenados de 1 a n
        for (int i = 0; i < filesSorted.length; i++)
            for(int j=0; j< files.length; j++){
                if(files[j].getName().equals((i +1) + ".png"))
                    filesSorted[i] = files[j];
            }

       
       BufferedImage[] imgs = new BufferedImage[filesSorted.length];
       
       for(int i = 0; i < imgs.length; i++)
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
           
           
       return imgs;
    }
   
    
}
