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
    //Novo objeto responsavel por desenhar cenario do jogo
    ///private LevelManager levelManager;
    // No initClasses colocar: levelManager = new LevelManager(this);
    // No update colocar: levelManager.update();
    // No render colocar: levelManager.draw(g) em cima do player;
    
    
    
    //Dimencionamentos finais de todo o jogo
    public final static int TILES_DEFAULT_SIZE = 32; //Tamanho padrao dos blocos
    public final static float SCALE = 1.5f;
    public final static int TILES_IN_WIDTH = 26; //Largura dos blocos
    public final static int TILES_IN_HEIGHT = 14; // Altura dos blocos
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE); //Tamanho real dos blocos
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH; // Largura do Jogo
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT; // Altura do jogo
    // No setPanelSize(classe GamePanel) colocar: Dimension size = new Dimension (GAME_WIDTH,GAME_HEIGHT) 
    
    
    //Construtor
    public Game() {
        gPanel = new GamePanel();
        gWindow = new GameWindow(gPanel);
        gPanel.requestFocus();                                  // determina prioridade para que o componente gPanel receba as entradas do teclado
    }
}
