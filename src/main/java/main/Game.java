//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package main;

import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import java.awt.Graphics;

//Classe que irá executar todo o jogo

//implementa interface Runnable para executar o loop de jogo numa nova Thread
public class Game implements Runnable {

    //Interface gráfica
    private GameWindow gameWindow;
    private GamePanel gamePanel;

    //Estados do jogo
    private Playing playing;
    private Menu menu;
    
    //Processo de execução
    private Thread gameThread; //nova thread ("mini-processo")
    private static int FPS_SET = 120; //Quantidade de FPS que o jogo vai rodar
    private static int UPS_SET = 200; //Quantidade entre os updates visando estabilidade
    
    //Modifiers - Debug
    private static boolean fpsMod = false;
    private static int fpsModifier = 0;
    private static boolean upsMod = false;
    private static int upsModifier = 0;

    public Game() {
        initClasses();
        gamePanel.setFocusable(true);
        gamePanel.requestFocus(); //foca no jogo
        startGameLoop(); //executa o loop
    }

    //Método que instancia as classes fundamentais do jogo.
    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
    }

    //Executa o loop e inicia o processo em uma nova thread
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //Atualiza o jogo
    public void update() {

        //to-do:  inserçao do modo de administrador
        switch (Gamestate.state) {
            case MENU:
                //menu.update();
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPTIONS:
            case QUIT:
            default:
                System.exit(0);
                break;
        }
    }

    //Renderiza/Desenha o jogo
    public void render(Graphics g) {

        switch (Gamestate.state) {
            case MENU:
                //menu.update();
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            default:
                break;
        }

    }

    //Implementa a thread no qual o game vai ficar em loop
    @Override
    public void run() {
        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();
        //devido a quantidade de tempo ser mínima entre um frame e outro, durante o loop do game, buscando pela internet foi observado que usar nanosegundos seria melhor do que milisegundos
        double timePerFrame = 1000000000.0 / FPS_SET;
        //Já o update pega o tempo entre a última atualização entre os frames; tempo da frequência entre eles
        double timePerUpdate = 1000000000.0 / UPS_SET;
        
        //dentro do loop vai ser realizada uma diferença entre o frame atual menos o frame anterior, junto com a sincronização entre os frames e os updates
        long previousTime = System.nanoTime();

        double deltaU = 0;
        double deltaF = 0;

        //loop
        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }
            //a implementação dos deltas se baseia em não haver perda de tempo entre os updates e os frames

            //contador de frames: 
            //currentTimeMilis nos retorna a quantidade de milisegundos desde o 'instante zero', também chamado de Unix Epoch, até a data atual
            //pegamos ele e diminuímos com o lastCheck, 
            //lastCheck é o tempo desde a última vez que entrou dentro do if
            //frames = 0 : reseta o contador de frame. Se não, ele mostraria a quantidade de frames gerados desde a inicialização do programa
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS " + frames + " |  UPS: " + updates);
                frames = 0;
                updates = 0;
            }

        }
    }

    //Se o jogo estiver no estado PLAYING, o movimento do jogador vai parar quando o jogo não estiver em foco no computador
    public void windowFocusLost() {
        if(Gamestate.state == Gamestate.PLAYING){
            playing.getPlayer().resetDirBooleans();
        }
    }
    
    public Menu getMenu(){
        return menu;
    }
    
    public Playing getPlaying(){
        return playing;
    }

}
