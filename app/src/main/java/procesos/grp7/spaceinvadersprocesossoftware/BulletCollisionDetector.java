package procesos.grp7.spaceinvadersprocesossoftware;

import android.view.View;
import android.widget.ImageView;

import java.util.List;

public class BulletCollisionDetector extends CollisionDetector implements Runnable {

    private Bullet bullet;
    private List<ImageView> gameViews;
    private PlayActivity activity;
    private boolean fromMarciano;
    private List<ImageView> listaMarcianos;
    private ImageView[] bordes; //0 arriba, 1 abajo

    public BulletCollisionDetector(Bullet bullet, List<ImageView> gameViews, PlayActivity activity, boolean fromMarciano, List<ImageView> listaMarcianos, ImageView[] bordes) {
        this.bullet = bullet;
        this.gameViews = gameViews;
        this.activity = activity;
        this.fromMarciano = fromMarciano;
        this.listaMarcianos = listaMarcianos;
        this.bordes = bordes;
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
            for (int i = 0; i < bordes.length; i++) {
                if (touch(bordes[i], bullet.getBulletView())) {
                    switch (i) {
                        case 0:
                            bullet.generateAnimation(Bullet.DOWN);
                            break;
                        case 1:
                            bullet.generateAnimation(Bullet.UP);
                            break;
                    }
                }
            }
            actualTime = System.currentTimeMillis();
            aliveTime = actualTime - startTime;
        }
    }

    public void setFromMarciano(boolean fromMarciano) {
        this.fromMarciano = fromMarciano;
    }
}
