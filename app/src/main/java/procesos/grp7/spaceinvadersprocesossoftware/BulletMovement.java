package procesos.grp7.spaceinvadersprocesossoftware;

import android.util.Log;
import android.widget.ImageView;

public class BulletMovement implements Runnable {
    private ImageView bulletView;
    private final int SPEED = 100;
    private final String BULLET_ERROR = "BULLET_ERROR";

    public BulletMovement(ImageView bulletView) {
        this.bulletView = bulletView;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(SPEED);
                bulletView.setY(bulletView.getY() + 1);
            }
        } catch (Exception e) {
            Log.e(BULLET_ERROR, e.getMessage());
        }
    }
}
