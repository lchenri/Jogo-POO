//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package gamestates;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import entities.Player;

/**
 *
 * @author lucas
 */
public class Admin extends JFrame {

    private JPanel panel;
    private String input;
    private float value;
    private int threadValue;

    public Admin() {
        draw();
    }

//    private float stringToInt(String input) {
//
//        return 0;
//    }

    public void draw() {
        setTitle("Janela de Administrador - Debug");
        setLayout(new FlowLayout());
        drawButton();

        setSize(700, 350);
        setVisible(true);

    }

    private void drawButton() {
        JPanel buttons = new JPanel();
        buttons.setPreferredSize(new Dimension(400, 200));

        JButton jumpHeight = new JButton("Altura Pulo");
        jumpHeight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                input = JOptionPane.showInputDialog("Coloque um valor que será somado ao valor padrao: ", Player.getJumpMultiplier());
                value = Float.parseFloat(input);
                Player.setJump(value);
            }
        });

        JButton gravity = new JButton("Gravidade");
        gravity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                input = JOptionPane.showInputDialog("Coloque um valor que será somado ao valor padrao: ", Player.getGravityMultiplier());
                value = Float.parseFloat(input);
                Player.setGravityMultiplier(value);
            }
        });

        JButton speed = new JButton("Velocidade");
        speed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                input = JOptionPane.showInputDialog("Coloque um valor que será somado ao valor padrao: ", Player.getSpeedMultiplier());
                value = Float.parseFloat(input);
                Player.setSpeed(value);
            }
        });

        JButton puloAposColisao = new JButton("PuloAposColisao");
        puloAposColisao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                input = JOptionPane.showInputDialog("Coloque um valor que será somado ao valor padrao: ", Player.getFallSpeedMultiplier());
                value = Float.parseFloat(input);
                Player.setFallSpeedMultiplier(value);
            }
        });
        
//                          Exemplo para adicionar novo botão no painel de adm
//        JButton UPS = new JButton("UPS");
//        UPS.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                input = JOptionPane.showInputDialog("Coloque um valor que será somado ao valor padrao: ", Game.getUps());
//                threadValue = Integer.parseInt(input);
////                Game.setUps(threadValue);
//            }
//        });

        buttons.add(jumpHeight);
        buttons.add(gravity);
        buttons.add(speed);
        buttons.add(puloAposColisao);
//        buttons.add(UPS);

        JScrollPane jscroll = new JScrollPane(buttons);
        jscroll.setPreferredSize(new Dimension(400, 300));
        add(jscroll);
    }

}
