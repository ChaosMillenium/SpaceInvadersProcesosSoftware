package procesos.grp7.spaceinvadersprocesossoftware;

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

    public void generateView(float coordsX, float sizeShipX, float coordsY, int id) {
            final ImageView bulletView = new ImageView(context);
            bulletView.setImageResource(R.drawable.bullet);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            if(this.direction == Bullet.UP) {
                params.addRule(RelativeLayout.ABOVE, id);
            }
            else{
                params.addRule(RelativeLayout.BELOW, id);
            }
            bulletView.setX(coordsX + (sizeShipX / 2) - 2);
            Log.d("YDisparo", "El disparo tiene y " + coordsY);
            bulletView.setY(coordsY);
            Log.d("YDisparo", "El disparo se dibujara en y " + bulletView.getY());
            params.setMargins(0, (int) coordsY, 0, 5);
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


    public void delete(){
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
