package procesos.grp7.spaceinvadersprocesossoftware;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Iterator;
import java.util.List;

public class Bullet {

    private PlayActivity context;
    private RelativeLayout gameLayout;
    public static final int UP = -1;
    public static final int DOWN = 1;
    private int direction; //-1 hacia arriba, 1 hacia abajo
    public static final int DURATION = 2000;
    private Point screenSize;
    private ImageView bulletView;
    private List<ImageView> gameViews, vistasMarcianos;
    private BulletCollisionDetector collisionDetector;
    private boolean fromMarciano;

    public Bullet(PlayActivity context, RelativeLayout gameLayout, int direction, List<ImageView> gameViews, List<ImageView> vistasMarcianos, boolean fromMarciano) {
        this.context = context;
        this.gameLayout = gameLayout;
        this.direction = direction;
        screenSize = new Point();
        context.getWindowManager().getDefaultDisplay().getSize(screenSize);
        this.gameViews = gameViews;
        this.vistasMarcianos = vistasMarcianos;
        this.fromMarciano = fromMarciano;
    }

    public void generateView(float coordsX, float sizeShipX, float coordsY, int id) {
        final ImageView bulletView = new ImageView(context);
        bulletView.setImageResource(R.drawable.bullet);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (this.direction == Bullet.UP) {
            params.addRule(RelativeLayout.ABOVE, id);
        } else {
            params.addRule(RelativeLayout.BELOW, id);
        }
        bulletView.setX(coordsX + (sizeShipX / 2) - 2);
        bulletView.setY(coordsY);
        params.setMargins(0, (int) coordsY, 0, 5);
        gameLayout.addView(bulletView, params);
        this.bulletView = bulletView;
        //ObjectAnimator puede dar problemas a la hora de comprobar colisiones,

        ObjectAnimator bulletAnimator = ObjectAnimator.ofFloat(bulletView, "translationY", 0f, (screenSize.y) * direction);
        bulletAnimator.setDuration(DURATION);
        bulletAnimator.setInterpolator(new LinearInterpolator());
        bulletAnimator.start();
        collisionDetector = new BulletCollisionDetector(this, gameViews, context, fromMarciano, vistasMarcianos);
        Thread collisionDetectorThread = new Thread(collisionDetector);
        collisionDetectorThread.start();
    }


    public void delete() {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameLayout.removeView(bulletView);
            }
        });
    }

    public ImageView getBulletView() {
        return bulletView;
    }
}
