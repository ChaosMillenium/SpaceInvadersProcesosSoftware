package procesos.grp7.spaceinvadersprocesossoftware;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

public class FallingInvaders extends Thread {
    private int screenX;
    private int screenY;
    private PlayActivity context;
    private RelativeLayout layout;
    private List<ImageView> gameViews;

    public FallingInvaders(PlayActivity context, int screenX, int screenY, RelativeLayout layout, List<ImageView> gameViews) {
        this.screenX = screenX;
        this.screenY = screenY;
        this.context = context;
        this.layout = layout;
        this.gameViews = gameViews;
    }


    public void run() {
        try {
            while (!context.dead) {
                this.context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MarcianoUnder13 marciano = new MarcianoUnder13(context, layout, screenX, screenY, gameViews);
                        int randomX = (int) (Math.random() * screenX - marciano.getWidth()) + 1;
                        marciano.generateMarciano(randomX);
                    }
                });
                Thread.sleep(1000);

            }
        } catch (InterruptedException ex) {
            System.out.println(ex.getCause());
        }
    }
}