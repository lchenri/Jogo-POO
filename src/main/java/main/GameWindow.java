//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.JFrame;

//Classe que cria a janela para renderizar o jogo

public class GameWindow {

    private JFrame jframe;

    public GameWindow(GamePanel gamePanel){
        jframe = new JFrame();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //termina execucao do codigo quando fecha janela        
        jframe.add(gamePanel); //insere panel na janela
        jframe.setResizable(false); //evita a janela mudar de tamanho
        jframe.pack(); //adequa janela ao tamanho do panel
        jframe.setLocationRelativeTo(null); //janela no centro da tela
        jframe.setVisible(true); //mostra janela
        jframe.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost(); //caso a janela perca o foco, chama o metodo da classe Game
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {
                
            }
        });

    }

}
