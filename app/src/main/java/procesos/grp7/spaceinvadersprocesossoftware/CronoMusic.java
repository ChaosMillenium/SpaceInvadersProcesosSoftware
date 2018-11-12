package procesos.grp7.spaceinvadersprocesossoftware;

import android.media.MediaPlayer;

public class CronoMusic extends Thread {
    private MediaPlayer music;
    private MenuActivity activity;
    private int songs[];

    public CronoMusic(MediaPlayer m, MenuActivity activity){
        this.music = m;
        this.activity = activity;
        this.songs = new int[3];
        rellenaSongs();
    }

    private void rellenaSongs(){
        this.songs[0] = R.raw.shovel;
        this.songs[1] = R.raw.got;
        this.songs[2] = R.raw.undertale;
    }

    public void run(){
        int i = 0;
        while (true){
            try {
                this.music.start();
                Thread.sleep(20000);
                if(i == 2){
                    i=0;
                }
                else {
                    i++;
                }
                this.music.stop();
                this.music = MediaPlayer.create(activity, this.songs[i]);
            }catch (InterruptedException e){
                e.getCause();
            }
        }
    }
}