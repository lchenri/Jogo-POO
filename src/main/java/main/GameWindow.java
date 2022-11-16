//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)
/*
Nesta classe, terá as configurações do JFrame
Não será só essa classe que irá renderizar os conteúdos do jogo
*/
package main;

import javax.swing.JFrame;

public class GameWindow extends JFrame{
    private JFrame jframe;

    //construtor
    public GameWindow(GamePanel gamePanel){
        jframe = new JFrame();
        
        jframe.setSize(400, 400);                                       // tamanho da janela
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             // quando fecha a janela, encerra a execução          
        jframe.add(gamePanel);                                                 // adiciona o frame(ou desenho) gerado no GamePanel no JFrame
        jframe.setLocationRelativeTo(null);                                      // abre a janela do jogo no centro da tela
        
        
        jframe.setVisible(true);                                                 // mostra a janela aberta (sempre precisa estar no final do código)
    }

}
