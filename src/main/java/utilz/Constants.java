//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package utilz;

import static utilz.Constants.GameConstants.*;

public class Constants {
    
    public static class EnemyConstants {
        public static final int ENEMY = 0;
        
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;
        
        public static final int CRABBY_WIDTH_DEFAULT = 72;
        public static final int CRABBY_HEIGHT_DEFAULT = 32;
        
        public static final int CRABBY_WIDTH = (int)(CRABBY_WIDTH_DEFAULT * SCALE);
        public static final int CRABBY_HEIGHT = (int)(CRABBY_HEIGHT_DEFAULT * SCALE);
        
        public static final int CRABBY_DRAWOFFSET_X = (int)(26 * SCALE);
        public static final int CRABBY_DRAWOFFSET_Y = (int)(9 * SCALE);
        
        public static int getSpriteAmount(int enemy_state) {
            
            switch(enemy_state) {
                case IDLE:
                    return 9;
                case RUNNING:
                    return 6;
                case ATTACK:
                    return 7;
                case HIT:
                    return 4;
                case DEAD:
                    return 5;
            }
            
            return 0;
        }
        
        public static int getMaxHealth() {
            return 10;
        }
        
        public static int getEnemyDmg() {
            return 15;
        }
    }
    
    public static class UI{
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * SCALE);
        }
        public static class PauseButtons{
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * SCALE);
        }
        
        public static class URMButtons{
            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * SCALE);
        }
    }
    
    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }
    
    public static class PlayerConstants {
        
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int ATTACK = 4;
        public static final int HIT = 5;
        public static final int DEAD = 6;
        
        public static int getSpriteAmount(int playerAction) {
            
            switch(playerAction) {
                case DEAD:
                    return 8;
                case RUNNING:
                    return 6;
                case IDLE:
                    return 5;
                case HIT:
                    return 4;
                case JUMP:
                case ATTACK:
                    return 3;
                case FALLING:
                default:
                    return 1;
                    
            }
        }
    }
    
    public static class GameConstants {
        
        //Constantes de dimensionamento para todo o jogo
        public final static int TILES_DEFAULT_SIZE = 32; //Tamanho padrao dos blocos
        public final static float SCALE = 1.5f; //Escala do jogo (constante para padronizar a proporção de todas as dimensões) 
        public final static int TILES_IN_WIDTH = 26; //Largura dos blocos
        public final static int TILES_IN_HEIGHT = 14; // Altura dos blocos
        public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE); //Tamanho real dos blocos
        public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH; // Largura do Jogo
        public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT; // Altura do jogo
    }
}
