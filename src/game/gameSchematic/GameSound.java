package game.gameSchematic;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineEvent.Type;

public abstract class GameSound {
    /**
     * 0 -> agree
     * 1 -> cannon
     * 2 -> emptyGun
     * 3 -> endOfGame
     * 4 -> EnemyBulletToMyTank
     * 5 -> enemydestroyed
     * 6 -> enemyshot
     * 7 -> gameOver
     * 8 -> gameSound1
     * 9 -> gameSound2
     * 10 -> heavygun
     * 11 -> lightgun
     * 12 -> mashingun
     * 13 -> mine
     * 14 -> MineBoom
     * 15 -> motor1
     * 16 -> recosh
     * 17 -> repair
     * 18 -> select
     * 19 -> softwall
     */

    private static String[] urls = {"Sounds/agree.wav", "Sounds/cannon.wav", "Sounds/emptyGun.wav", "Sounds/endOfGame.wav"
            , "Sounds/EnemyBulletToMyTank.wav", "Sounds/enemydestroyed.wav", "Sounds/enemyshot.wav", "Sounds/gameOver.wav"
            , "Sounds/gameSound1.wav", "Sounds/gameSound2.wav", "Sounds/heavygun.wav", "Sounds/lightgun.wav", "Sounds/mashingun.wav"
            , "Sounds/mine.wav", "Sounds/MineBoom.wav", "Sounds/motor1.wav", "Sounds/recosh.wav", "Sounds/repair.wav"
            , "Sounds/select.wav", "Sounds/softwall.wav"};

    private static Thread tr = null;
    private static Sound s;

    public static void playSound(int i){
        File clipFile = null;
        clipFile = new File(urls[i]);
        s = new Sound(clipFile);
        tr = new Thread(s);
        tr.start();
    }

    public static void playGameSound2(){
        File gameSound2File = new File(urls[9]);
        s = new Sound(gameSound2File);
        tr = new Thread(s);
        tr.start();
        System.out.println("here2");
    }

    public static void pauseGameSound2(){
        tr.interrupt();
    }

//    public static void pauseGameSound2(){
//        try {
//            synchronized(gameSound2) {
//                gameSound2.wait();
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    private static class Sound implements Runnable{
        private File clipFile;
        public Sound(File clipFile){
            this.clipFile = clipFile;
        }
        class AudioListener implements LineListener {
            private boolean done = false;

            @Override
            public synchronized void update(LineEvent event) {
                Type eventType = event.getType();
                if (eventType == Type.STOP || eventType == Type.CLOSE) {
                    done = true;
                    notifyAll();
                }
            }

            public synchronized void waitUntilDone() {
                while (!done) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        done = true;
                    }
                }
            }
        }
        @Override
        public void run() {
            AudioListener listener = new AudioListener();
            Clip clip = null;
            AudioInputStream audioInputStream = null;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(clipFile);
                clip = AudioSystem.getClip();
                clip.addLineListener(listener);
                clip.open(audioInputStream);
                clip.start();
                listener.waitUntilDone();
                audioInputStream.close();
                clip.close();

            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
    }
}
