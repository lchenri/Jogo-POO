//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

/*
Classe que define o modo de jogo, executando as acoes dos listeners e atualizando a tela de acordo
 */
package gamestates;

import entities.EnemyManager;
import entities.Player;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import levels.LevelManager;
import main.Game;
import ui.LevelCompletedOverlay;
import java.awt.geom.Rectangle2D;
import ui.GameOverOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;
import static utilz.Constants.GameConstants.*;

public class Playing extends State implements Statemethods {

    private Player player; //objeto personagem
    private LevelManager levelManager; //novo objeto responsavel por desenhar cenario do jogo
    private EnemyManager enemyManager;
    private boolean paused = false;
    private PauseOverlay pauseOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
            
    private BufferedImage backgroundImg;
    private boolean lvlCompleted;
    private int maxLvlOffsetX;
    private GameOverOverlay gameOverOverlay;
    private boolean gameOver;

    public Playing(Game game) {
        super(game);
        initClasses();
        pauseOverlay = new PauseOverlay(this);
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
        
        calcLvlOffset();
        loadStartLevel();
    }
    
    public void loadNextLevel() {
        resetAll();
        levelManager.loadNextLevel();
    }
    
    private void loadStartLevel(){
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
    }
    
    private void calcLvlOffset(){
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }

    public void unpauseGame() {
        paused = false;
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        player = new Player(200, 200, (int) (64 * SCALE), (int) (40 * SCALE), this);
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        levelCompletedOverlay = new LevelCompletedOverlay(this);
    }

    @Override
    public void update() {

        // Mudei as verificacoes nesse update, o game over ja esta implementado nao mexe
        if (paused) {
            pauseOverlay.update();
        } else if(lvlCompleted) {
            levelCompletedOverlay.update();
        } else if(!gameOver){
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            //checkCloseToBorder();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0,GAME_WIDTH ,GAME_HEIGHT, null);
        levelManager.draw(g);
        player.render(g);
        enemyManager.draw(g);
        if (paused) {
            pauseOverlay.draw(g);
        } else if(lvlCompleted)
            levelCompletedOverlay.draw(g);
    }
    
    public void resetAll(){ //ADICIONAR LVLCOMPLETED = FALSE
        gameOver = false;
        paused = false;
        lvlCompleted = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
    }
    
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    
    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!gameOver)
            if (e.getButton() == MouseEvent.BUTTON1) {
                player.setAttacking(true); //ataca
            }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        if(!gameOver){  
            if (paused) {
                pauseOverlay.mousePressed(e);
            } else if(lvlCompleted)
                levelCompletedOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        if(!gameOver){  
            if (paused) {
                pauseOverlay.mouseReleased(e);
            }else if(lvlCompleted)
                levelCompletedOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
        if(!gameOver){      
            if (paused) {
                pauseOverlay.mouseMoved(e);
            }else if(lvlCompleted)
                levelCompletedOverlay.mouseMoved(e);
        }
    }
    
    public void setLevelCompleted(boolean levelCompleted){
        this.lvlCompleted = levelCompleted;
    }
    
    public void setMaxLvlOffset(int lvlOffset){
        this.maxLvlOffsetX = lvlOffset;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver)
            gameOverOverlay.keyPressed(e);
        else
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
            }
        if (e.getKeyCode() == KeyEvent.VK_G) {
            Admin adm = new Admin();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver)
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
    
    public EnemyManager getEnemyManager(){
        return enemyManager;
    }
}
