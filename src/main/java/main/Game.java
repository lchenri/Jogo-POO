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
    private final int FPS_SET = 120; //constante pra FPS
    
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
        
        //devido a quantidade de tempo ser mínima entre um frame e outro, durante o loop do game, buscando pela internet foi observado que usar nanosegundos seria melhor do que milisegundos
        double timePerFrame = 1000000000.0 / FPS_SET;
        
        //dentro do loop vai ser realizada uma diferença entre o frame atual menos o frame anterior
        long lastFrame = System.nanoTime();
        long now;
        
        int frames = 0;
        long lastCheck = System.currentTimeMillis();
        
        while(true) {
            
            //loop
            now = System.nanoTime();
            if(now - lastFrame >= timePerFrame) {
                update();
                gamePanel.repaint();
                lastFrame = now;
                frames++;
            }
            
            //contador de frames:
            //currentTimeMilis nos retorna a quantidade de milisegundos desde o 'instante zero', também chamado de Unix Epoch, até a data atual
            //pegamos ele e diminuímos com o lastCheck, 
            //lastCheck é o tempo desde a última vez que entrou dentro do if
            //frames = 0 : reseta o contador de frame. Se não, ele mostraria a quantidade de frames gerados desde a inicialização do programa
            if(System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames);
                frames = 0;
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
