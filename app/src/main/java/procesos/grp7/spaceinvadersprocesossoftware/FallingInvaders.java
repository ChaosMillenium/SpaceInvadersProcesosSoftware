package procesos.grp7.spaceinvadersprocesossoftware;

import android.app.Activity;
import android.widget.RelativeLayout;

public class FallingInvaders extends Thread{
    private int screenX;
    private int screenY;
    private Activity context;
    private RelativeLayout layout;

    public FallingInvaders(Activity context, int screenX, int screenY, RelativeLayout layout){
        this.screenX = screenX;
        this.screenY = screenY;
        this.context = context;
        this.layout = layout;
    }


    public void run(){
        try {
            while (true) {
                this.context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MarcianoUnder13 marciano = new MarcianoUnder13(context,layout, screenX, screenY);
                        int randomX = (int)(Math.random()*screenX-marciano.getWidth())+1;
                        marciano.generateMarciano(randomX);
                    }
                });
                Thread.sleep(500);

            }
        }catch(InterruptedException ex){System.out.println(ex.getCause());}
    }
}