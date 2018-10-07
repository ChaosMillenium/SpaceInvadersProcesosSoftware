package procesos.grp7.spaceinvadersprocesossoftware;

import android.widget.ImageView;

import java.util.List;

public class DefensaCollisionDetector extends CollisionDetector implements Runnable {
    private PlayActivity activity;
    private List<ImageView> gameViews;
    private Defensas defense;

    public DefensaCollisionDetector(PlayActivity activity, List<ImageView> gameViews, Defensas defense) {
        this.activity = activity;
        this.gameViews = gameViews;
        this.defense = defense;
    }

    @Override
    public void run() {
        while (!activity.dead) {
            ImageView collider = detectCollision(gameViews, defense.getSprite());
            if (collider != null) {
                activity.kill(defense, collider);
                return;
            }
        }
    }
}
