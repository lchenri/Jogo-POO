//Leonorico Eduardo de Paula Borges (202135032)
//Lucas Henrique de Araujo Cardoso (202135038)
//Pedro Lucas Botelho Freitas (202135040)

package utilz;

import main.Game;

public class Constants {
    
    public static class EnemyConstants {
        public static final int CRABBY = 0;
        
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;
        
        public static final int CRABBY_WIDTH_DEFAULT = 72;
        public static final int CRABBY_HEIGHT_DEFAULT = 32;
        
        public static final int CRABBY_WIDTH = (int)(CRABBY_WIDTH_DEFAULT * Game.SCALE);
        public static final int CRABBY_HEIGHT = (int)(CRABBY_HEIGHT_DEFAULT * Game.SCALE);
        
        public static final int CRABBY_DRAWOFFSET_X = (int)(26 * Game.SCALE);
        public static final int CRABBY_DRAWOFFSET_Y = (int)(9 * Game.SCALE);
        
        public static int getSpriteAmount(int enemy_type, int enemy_state) {
            
            switch(enemy_type) {
                case CRABBY:
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
            }
            
            return 0;
        }
        
        public static int getMaxHealth(int enemy_type) {
            switch(enemy_type) {
                case CRABBY:
                    return 10;
                default:
                    return 1;
            }
        }
        
        public static int getEnemyDmg(int enemy_type) {
            switch(enemy_type) {
                case CRABBY:
                    return 15;
                default:
                    return 0;
            }
        }
    }
    
    public static class UI{
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }
        public static class PauseButtons{
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
        }
        
        public static class URMButtons{
            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);
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
}
