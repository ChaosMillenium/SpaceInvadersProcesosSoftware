package procesos.grp7.spaceinvadersprocesossoftware;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Bullet extends Thread {

    private Activity context;
    private RelativeLayout gameLayout;
    private ImageView bulletView;
    public static final int UP = -1;
    public static final int DOWN = 1;
    private int direction; //-1 hacia arriba, 1 hacia abajo
    private final int SLEEPTIME = 2;
    private final String ERROR_LOG = "BULLET_ERROR";
    private Point screenSize;

    public Bullet(Activity context, RelativeLayout gameLayout, int direction, Point screenSize) {
        this.context = context;
        this.gameLayout = gameLayout;
        this.direction = direction;
        this.screenSize = screenSize;
    }

    public void generateView() {
        ImageView bulletView = new ImageView(context);
        bulletView.setImageResource(R.drawable.bullet);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ABOVE, R.id.ship);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        gameLayout.addView(bulletView, params);
        this.bulletView = bulletView;
    }

    public void update() {
        bulletView.setY(bulletView.getY() + direction);
    }

    @Override
    public void run() {
        int maxY = screenSize.y;
        try {
            while ((maxY >= bulletView.getY()) && (bulletView.getY() >= 0)) {
                Thread.sleep(SLEEPTIME);
                update();
            }
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameLayout.removeView(bulletView);
                }
            });
        } catch (InterruptedException e) {
            Log.e(ERROR_LOG, e.getMessage());
        }
    }
}
