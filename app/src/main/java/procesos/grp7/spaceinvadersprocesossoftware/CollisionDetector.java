package procesos.grp7.spaceinvadersprocesossoftware;

import android.view.View;
import android.widget.ImageView;

import java.util.Iterator;

public abstract class CollisionDetector {
    public synchronized ImageView detectCollision(Iterable<ImageView> views, ImageView collider) {
        synchronized (views) {
            Iterator<ImageView> i = views.iterator();
            while (i.hasNext()) {
                ImageView view = i.next();
                if ((view != collider) && (view != null)) {
                    if ((collider.getY() > 0) && touch(collider, view)) {
                        return view;
                    }
                }
            }
        }
        return null;
    }

    protected boolean touch(View collider, View view) {
        float x = view.getX();
        float y = view.getY();
        float width = view.getWidth();
        float height = view.getHeight();
        float maxX = x + width;
        float maxY = y + height;
        return ((collider.getX()+collider.getWidth() >= x) && (collider.getX() <= maxX) && (collider.getY()+collider.getHeight() >= y) && (collider.getY() <= maxY));
    }
}
