package procesos.grp7.spaceinvadersprocesossoftware;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Bullet {

    private Activity context;
    private RelativeLayout gameLayout;
    public static final int UP = -1;
    public static final int DOWN = 1;
    private int direction; //-1 hacia arriba, 1 hacia abajo
    private final int DURATION = 1000;
    private Point screenSize;
    private ImageView bulletView;
    private float yPosition;

    public Bullet(Activity context, RelativeLayout gameLayout, int direction) {
        this.context = context;
        this.gameLayout = gameLayout;
        this.direction = direction;
        screenSize = new Point();
        context.getWindowManager().getDefaultDisplay().getSize(screenSize);
    }

    public void generateView(float coordsX, float sizeShipX) {
        ImageView bulletView = new ImageView(context);
        bulletView.setImageResource(R.drawable.bullet);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ABOVE, R.id.ship);
        bulletView.setX(coordsX + (sizeShipX / 2)-2);
        params.setMargins(0, 0, 0, 5);
        gameLayout.addView(bulletView, params);
        this.bulletView = bulletView;
        //ObjectAnimator puede dar problemas a la hora de comprobar colisiones,
        ObjectAnimator bulletAnimator = ObjectAnimator.ofFloat(bulletView, "translationY", 0f, screenSize.y * direction);
        bulletAnimator.setDuration(DURATION);
        bulletAnimator.setInterpolator(new LinearInterpolator());
        bulletAnimator.start();
        bulletAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                yPosition = (Float) animation.getAnimatedValue();
            }
        });
    }

    @Nullable
    public View detectCollision(Iterable<View> views) {
        Rect bullet = new Rect(bulletView.getLeft(), bulletView.getTop(), bulletView.getRight(), bulletView.getBottom());
        for (View view : views) {
            Rect viewRect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            if (bullet.intersect(viewRect)) {
                return view;
            }
        }
        return null;
    }

    public synchronized boolean isInScreen() {
        return (yPosition >= 0) && (yPosition <= screenSize.y);
    }
}
