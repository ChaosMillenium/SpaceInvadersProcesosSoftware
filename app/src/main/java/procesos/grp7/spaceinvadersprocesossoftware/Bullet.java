package procesos.grp7.spaceinvadersprocesossoftware;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Bullet{

    private Activity context;
    private RelativeLayout gameLayout;
    public static final float UP = -1;
    public static final float DOWN = 1;
    private float direction; //-1 hacia arriba, 1 hacia abajo
    private final int DURATION = 1000;
    private Point screenSize;

    public Bullet(Activity context, RelativeLayout gameLayout, float direction) {
        this.context = context;
        this.gameLayout = gameLayout;
        this.direction = direction;
        screenSize = new Point();
        context.getWindowManager().getDefaultDisplay().getSize(screenSize);
    }

    public void generateView() {
        ImageView bulletView = new ImageView(context);
        bulletView.setImageResource(R.drawable.bullet);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ABOVE, R.id.ship);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        gameLayout.addView(bulletView, params);
        //ObjectAnimator puede dar problemas a la hora de comprobar colisiones,
        ObjectAnimator bulletAnimator = ObjectAnimator.ofFloat(bulletView, "translationY",0f, screenSize.y*direction);
        bulletAnimator.setDuration(DURATION);
        bulletAnimator.setInterpolator(new LinearInterpolator());
        bulletAnimator.start();
    }
}
