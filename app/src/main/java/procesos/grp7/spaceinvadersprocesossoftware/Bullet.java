package procesos.grp7.spaceinvadersprocesossoftware;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.Log;
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
    public static final int DURATION = 1000;
    private Point screenSize;
    private ImageView bulletView;
    private float yPosition = 0, xPosition = 0;

    public Bullet(Activity context, RelativeLayout gameLayout, int direction) {
        this.context = context;
        this.gameLayout = gameLayout;
        this.direction = direction;
        screenSize = new Point();
        context.getWindowManager().getDefaultDisplay().getSize(screenSize);
    }

    public void generateView(float coordsX, float sizeShipX) {
        final ImageView bulletView = new ImageView(context);
        bulletView.setImageResource(R.drawable.bullet);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ABOVE, R.id.ship);
        bulletView.setX(coordsX + (sizeShipX / 2) - 2);
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
                xPosition = bulletView.getX();
                yPosition = bulletView.getY();
            }
        });
    }

    public View detectCollision(Iterable<View> views) {
        for (View view : views) {
            Log.d("POSITION_LOG", xPosition + ", " + yPosition + ":" + view.getX() + ", " + view.getY());
            if (touch(view.getX(), view.getY(), view.getWidth(), view.getHeight())) {
                bulletView.setVisibility(View.INVISIBLE);
                return view;
            }
        }
        return null;
    }

    private boolean touch(float x, float y, float width, float height) {
        float maxX = x + width;
        float maxY = y + height;
        return ((xPosition >= x) && (xPosition <= maxX) && (yPosition >= y) && (yPosition <= maxY));
    }

    //Versión antigua no funcional
    /*
    @Nullable
    public View detectCollision(Iterable<View> views) {
        for (View view : views) {
            Rect bullet = new Rect(bulletView.getLeft(), bulletView.getTop(), bulletView.getRight(), bulletView.getBottom());
            Log.d("BULLET_HITBOX", bullet.toString());
            Rect viewRect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            Log.d("BULLET_HITBOX", view.toString());
            if (bullet.intersect(viewRect)) {
                bulletView.setVisibility(View.INVISIBLE);
                return view;
            }
        }
        return null;
    }
*/
}
