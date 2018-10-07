package procesos.grp7.spaceinvadersprocesossoftware;

import android.widget.ImageView;

import java.util.List;

public class BulletCollisionDetector extends CollisionDetector implements Runnable {

    private Bullet bullet;
    private List<ImageView> gameViews;
    private GameActivity activity;
    private boolean fromMarciano;
    private List<ImageView> listaMarcianos;

    public BulletCollisionDetector(Bullet bullet, List<ImageView> gameViews, GameActivity activity, boolean fromMarciano, List<ImageView> listaMarcianos) {
        this.bullet = bullet;
        this.gameViews = gameViews;
        this.activity = activity;
        this.fromMarciano = fromMarciano;
        this.listaMarcianos = listaMarcianos;
    }

    @Override
    public void run() {
        long aliveTime = 0;
        long startTime = System.currentTimeMillis();
        long actualTime;
        while (aliveTime < Bullet.DURATION) {
            final ImageView collider = detectCollision(gameViews, bullet.getBulletView());
            if (collider != null) {
                if (fromMarciano) {
                    if (!listaMarcianos.contains(collider)) {
                        activity.kill(bullet, collider);
                        return;
                    }
                } else {
                    activity.kill(bullet, collider);
                    return;
                }
            }
            actualTime = System.currentTimeMillis();
            aliveTime = actualTime - startTime;
        }
    }
}
