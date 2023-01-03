//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private int xDelta = 100, yDelta = 100;
    private BufferedImage img, subImg;

    //construtor
    public GamePanel() {
        mouseInputs = new MouseInputs();
        importImg();
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }
    
    private void importImg() {
        File file = new File("res/player_sprites.png");
        System.out.println(file);
        try {
            img = ImageIO.read(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    private void setPanelSize() {
        Dimension size = new Dimension(1280,800);
        setPreferredSize(size);
    }

    public void changeXDelta(int value) {
        this.xDelta += value;
    }

    public void changeYDelta(int value) {
        this.yDelta += value;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        subImg = img.getSubimage(1*64, 8*40, 64, 40);
        g.drawImage(subImg, xDelta, yDelta, 128, 80, null);
    }
    
    public void exit() {
        System.exit(0);
    }
}
