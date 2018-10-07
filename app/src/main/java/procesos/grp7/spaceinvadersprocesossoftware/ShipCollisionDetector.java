package procesos.grp7.spaceinvadersprocesossoftware;

import android.util.Log;
import android.widget.ImageView;

import java.util.List;

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
            if (collider == null) {
                Log.d("MARCIANO_COLLISION", "No collision");
            } else {
                Log.d("MARCIANO_COLLISION", collider.toString());
                activity.kill(shipSprite, shipSprite);
                return;
            }
        }
    }
}
