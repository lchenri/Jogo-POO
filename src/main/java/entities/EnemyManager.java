//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package entities;

import gamestates.Playing;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {
    
    private Playing playing;
    private BufferedImage[][] crabbyArr;
    private ArrayList<Crabby> crabbies = new ArrayList<>();
    
    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
    }

    public void loadEnemies(Level level) {
        crabbies = level.getCrabbies();
    }
    
    public void update(int[][] lvlData, Player player) {
        boolean isAnyActive = false;
        for(Crabby crabby : crabbies)
            if(crabby.isActive()) {
                crabby.update(lvlData, player);
                isAnyActive = true;
            }
        if(!isAnyActive)
            playing.setLevelCompleted(true);
    }
    
    public void draw(Graphics g) {
        drawCrabs(g);
    }
    
    private void drawCrabs(Graphics g) {
        for(Crabby crabby : crabbies) 
            if(crabby.isActive()) {
                g.drawImage(crabbyArr[crabby.getEnemyState()][crabby.getAniIndex()], (int)crabby.getHitbox().x - CRABBY_DRAWOFFSET_X + crabby.flipX(), (int)crabby.getHitbox().y - CRABBY_DRAWOFFSET_Y, CRABBY_WIDTH * crabby.flipW(), CRABBY_HEIGHT, null);
//                crabby.drawAttackBox(g);
//                crabby.drawHitbox(g);
            }
    }
    
    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for(Crabby crabby : crabbies)
            if(crabby.isActive())
                if(attackBox.intersects(crabby.getHitbox())) {
                    crabby.hurt(10);
                    return;
                }
    }

    private void loadEnemyImgs() {
        crabbyArr = new BufferedImage[5][9];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE);
        for(int j = 0; j < crabbyArr.length; j++)
            for(int i = 0; i < crabbyArr[j].length; i++)
                crabbyArr[j][i] = temp.getSubimage(i * CRABBY_WIDTH_DEFAULT, j * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
    }

    public void resetAllEnemies() {
        for(Crabby crabby : crabbies)
            crabby.resetEnemy();
    }
}
