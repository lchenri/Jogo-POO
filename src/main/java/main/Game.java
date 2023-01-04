//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package main;

//implementa interface Runnable para executar o loop de jogo numa nova Thread

import entities.Player;
import java.awt.Graphics;

public class Game implements Runnable {

    private GameWindow gameWindow; //janela
    private GamePanel gamePanel; //cena (pintura/jogo)
    private Thread gameThread; //nova thread ("mini-processo")
    private final int FPS_SET = 120; //Quantidade de FPS que o jogo vai rodar
    private final int UPS_SET = 200; //Quantidade entre os updates visando estabilidade
    
    private Player player;

    //Construtor
    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus(); //foca no jogo
        startGameLoop(); //executa o loop
    }

    private void initClasses() {
        player = new Player(200,200);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public void update() {
        player.update();
    }
    
    public void render(Graphics g) {
        player.render(g);
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

        
        //

        //loop
        while (true) {
            long currentTime = System.nanoTime();
            
            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;
            
            if(deltaU >= 1){
                update();
                updates++;
                deltaU--;
            }
            
            if(deltaF >=1){
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

    public void windowFocusLost() {
        player.resetDirBooleans();
    }
    
    public Player getPlayer() {
        return player;
    }
}    
