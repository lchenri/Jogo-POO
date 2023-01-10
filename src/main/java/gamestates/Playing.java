/*
Classe que define o modo de jogo, executando as acoes dos listeners e atualizando a tela de acordo
 */
package gamestates;

import entities.EnemyManager;
import entities.Player;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import levels.LevelManager;
import main.Game;
import static main.Game.SCALE;
import ui.PauseOverlay;

/**
 *
 * @author lucas
 */
public class Playing extends State implements Statemethods {

    private Player player; //objeto personagem
    private LevelManager levelManager; //novo objeto responsavel por desenhar cenario do jogo
    private EnemyManager enemyManager;
    private boolean paused = false;
    private PauseOverlay pauseOverlay;

    public Playing(Game game) {
        super(game);
        initClasses();
        pauseOverlay = new PauseOverlay(this);
    }

    public void unpauseGame() {
        paused = false;
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE));
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
    }

    @Override
    public void update() {
        if (paused) {
            pauseOverlay.update();
        } else {
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
        }
    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g);
        player.render(g);
        enemyManager.draw(g);
        if (paused) {
            pauseOverlay.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            player.setAttacking(true); //ataca
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused) {
            pauseOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player.setUp(true);
                break;
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_S:
                player.setDown(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
            //ao pressionar as teclas a acao é realizada
//            case KeyEvent.VK_ESCAPE: //ao pressionar a tecla 'ESC', o jogo é encerrado
//                player.exit();
//                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player.setUp(false);
                break;
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_S:
                player.setDown(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
        }
        //Ao soltar a tecla, a acao para    
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }
}
