//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

/*
Neste enum contém os conjuntos de constantes que vão representar o que está acontecendo no jogo
 */
package gamestates;

public enum Gamestate {
    
    PLAYING, MENU, OPTIONS, QUIT;
    
    public static Gamestate state = MENU;
}
