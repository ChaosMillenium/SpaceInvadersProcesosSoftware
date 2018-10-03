package procesos.grp7.spaceinvadersprocesossoftware;

import android.util.Log;
import android.widget.ImageView;

import java.util.List;


public class MarcianoCollisionDetector extends CollisionDetector implements Runnable {

    private Marciano marciano;
    private List<ImageView> gameViews;
    private GameActivity activity;
    private List<ImageView> listaMarcianos;

    public MarcianoCollisionDetector(Marciano marciano, List<ImageView> gameViews, GameActivity activity, List<ImageView> listaMarcianos) {
        this.marciano = marciano;
        this.gameViews = gameViews;
        this.activity = activity;
        this.listaMarcianos = listaMarcianos;
    }

    @Override
    public void run() {
        while (!activity.dead) {
            final ImageView collider = detectCollision(gameViews, marciano.getSpriteMarciano());
            if (collider == null) {
                Log.d("MARCIANO_COLLISION", "No collision");
            } else {
                if (!listaMarcianos.contains(collider)) {
                    Log.d("MARCIANO_COLLISION", collider.toString());
                    activity.kill(marciano, collider);
                    return;
                }
            }
        }
    }
}
