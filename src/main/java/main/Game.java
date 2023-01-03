//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package main;

//implementa interface Runnable para executar o loop de jogo numa nova Thread
public class Game implements Runnable {

    private GameWindow gameWindow; //janela
    private GamePanel gamePanel; //cena (pintura/jogo)
    private Thread gameThread; //nova thread ("mini-processo")
    private final int FPS_SET = 120; //constante pra FPS

    //Construtor
    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus(); //foca no jogo
        startGameLoop(); //executa o loop
    }
    
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //executa o loop e o fps counter
    @Override
    public void run() {
        
        double timePerFrame = 1000000000.0 / FPS_SET;
        long lastFrame = System.nanoTime();
        long now;
        
        int frames = 0;
        long lastCheck = System.currentTimeMillis();
        
        while(true) {
            
            //loop
            now = System.nanoTime();
            if(now - lastFrame >= timePerFrame) {
                gamePanel.repaint();
                lastFrame = now;
                frames++;
            }
            
            //fps counter
            if(System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }
}
