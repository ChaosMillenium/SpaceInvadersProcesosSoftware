package procesos.grp7.spaceinvadersprocesossoftware;

import android.widget.ImageView;

import java.util.List;

import procesos.grp7.spaceinvadersprocesossoftware.PlayActivity;

public class ShipCollisionDetector extends CollisionDetector implements Runnable {
    private PlayActivity activity;
    private List<ImageView> gameViews;
    private ImageView shipSprite;

    public ShipCollisionDetector(PlayActivity activity, List<ImageView> gameViews, ImageView shipSprite) {
        this.activity = activity;
        this.gameViews = gameViews;
        this.shipSprite = shipSprite;
    }

    @Override
    public void run() {
        while (!activity.dead) {
            ImageView collider = detectCollision(gameViews, shipSprite);
            if (collider != null) {
                activity.kill(collider, shipSprite);
                return;
            }
        }
    }
}
