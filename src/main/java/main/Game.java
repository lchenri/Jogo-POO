//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)
/*
Dentro dessa classe teremos tudo. O jogo vai começar por esta classe.
 */
package main;

public class Game implements Runnable{

    //Novo objeto gamewindow, para abrir a janela
    private GameWindow gWindow;
    //Novo objeto gamepanel, para renderizar o jogo
    private GamePanel gPanel;
    //Inicializa a Thread
    private Thread gameThread;
    //Quantidade de FPS que o jogo vai rodar
    private final int FPS_SET = 120;

    //Construtor
    public Game() {
        gPanel = new GamePanel();
        gWindow = new GameWindow(gPanel);
        gPanel.requestFocus();                                  // determina prioridade para que o componente gPanel receba as entradas do teclado
        startGameLoop();
    }
    
    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    //Implementa a thread no qual o game vai ficar em loop
    @Override
    public void run() {
        
         //devido a quantidade de tempo ser mínima entre um frame e outro, durante o loop do game, buscando pela internet foi observado que usar nanosegundos seria melhor do que milisegundos
         double timePerFrame = 1000000000.0 / FPS_SET;
         
         //dentro do loop vai ser realizada uma diferença entre o frame atual menos o frame anterior
         long lastFrame = System.nanoTime();
         long now = System.nanoTime();
         
         //loop
         while(true){
            
             now = System.nanoTime();
             if(now - lastFrame >= timePerFrame){
                 gPanel.repaint();
                 lastFrame = now;
             }
         }
        
    }
}
