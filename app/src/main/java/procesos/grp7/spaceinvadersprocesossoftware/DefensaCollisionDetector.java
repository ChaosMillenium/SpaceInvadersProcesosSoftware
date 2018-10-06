package procesos.grp7.spaceinvadersprocesossoftware;

import android.util.Log;
import android.widget.ImageView;

import java.util.List;

public class DefensaCollisionDetector extends CollisionDetector implements Runnable{
    private GameActivity activity;
    private List<ImageView> gameViews;
    private Defensas defense;

    public DefensaCollisionDetector(GameActivity activity, List<ImageView> gameViews, Defensas defense) {
        this.activity = activity;
        this.gameViews = gameViews;
        this.defense = defense;
    }

    @Override
    public void run() {
        while (!activity.dead) {
            ImageView collider = detectCollision(gameViews, defense.getSprite());
            if (collider == null) {
                Log.d("MARCIANO_COLLISION", "No collision");
            } else {
                Log.d("MARCIANO_COLLISION", collider.toString());
                activity.kill(defense, collider);
                return;
            }
        }
    }
}
