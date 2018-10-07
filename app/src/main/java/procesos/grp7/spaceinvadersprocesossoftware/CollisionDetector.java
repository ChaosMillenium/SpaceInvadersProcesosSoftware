package procesos.grp7.spaceinvadersprocesossoftware;

import android.view.View;
import android.widget.ImageView;

import java.util.Iterator;

public abstract class CollisionDetector {
    public ImageView detectCollision(Iterable<ImageView> views, ImageView collider) {
        synchronized (views) {
            Iterator<ImageView> i = views.iterator();
            while (i.hasNext()) {
                ImageView view = i.next();
                if ((view != collider) && (view != null)) {
                    if ((collider.getY() > 0) && touch(collider, view.getX(), view.getY(), view.getWidth(), view.getHeight())) {
                        return view;
                    }
                }
            }
        }
        return null;
    }

    private boolean touch(View collider, float x, float y, float width, float height) {
        float maxX = x + width;
        float maxY = y + height;
        return ((collider.getX() >= x) && (collider.getX() <= maxX) && (collider.getY() >= y) && (collider.getY() <= maxY));
    }
}
