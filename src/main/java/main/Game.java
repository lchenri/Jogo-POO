//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)
/*
Dentro dessa classe teremos tudo. O jogo vai começar por esta classe.
 */
package main;

public class Game {

    //Novo objeto gamewindow, para abrir a janela
    private GameWindow gWindow;
    //Novo objeto gamepanel, para renderizar o jogo
    private GamePanel gPanel;

    //Construtor
    public Game() {
        gPanel = new GamePanel();
        gWindow = new GameWindow(gPanel);
        gPanel.requestFocus();                                  // determina prioridade para que o componente gPanel receba as entradas do teclado
    }
}
