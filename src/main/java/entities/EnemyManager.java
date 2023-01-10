package entities;

import gamestates.Playing;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {
    
    private Playing playing;
    private BufferedImage[][] crabbyArr;
    private ArrayList<Crabby> crabbies = new ArrayList<>();
    
    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
        addEnemies();
    }

    private void addEnemies() {
        crabbies = LoadSave.getCrabs();
    }
    
    public void update(int[][] lvlData) {
        for(Crabby crabby : crabbies)
            crabby.update(lvlData);
    }
    
    public void draw(Graphics g) {
        drawCrabs(g);
    }
    
    private void drawCrabs(Graphics g) {
        for(Crabby crabby : crabbies)
            g.drawImage(crabbyArr[crabby.getEnemyState()][crabby.getAniIndex()], (int)crabby.getHitbox().x, (int)crabby.getHitbox().y, CRABBY_WIDTH, CRABBY_HEIGHT, null);
    }

    private void loadEnemyImgs() {
        crabbyArr = new BufferedImage[5][9];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE);
        for(int j = 0; j < crabbyArr.length; j++)
            for(int i = 0; i < crabbyArr[j].length; i++)
                crabbyArr[j][i] = temp.getSubimage(i * CRABBY_WIDTH_DEFAULT, j * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
    }
}
