package procesos.grp7.spaceinvadersprocesossoftware;

import android.util.Log;
import android.view.View;
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
            if (collider == null) {
                Log.d("BULLET_COLLISION", "No collision");
            } else {
                if (fromMarciano) {
                    if (!listaMarcianos.contains(collider)) {
                        Log.d("BULLET_COLLISION", collider.toString());
                        activity.kill(bullet, collider);
                        return;
                    }
                } else {
                    Log.d("BULLET_COLLISION", collider.toString());
                    activity.kill(bullet, collider);
                    return;
                }
            }
            actualTime = System.currentTimeMillis();
            aliveTime = actualTime - startTime;
        }
    }
}
