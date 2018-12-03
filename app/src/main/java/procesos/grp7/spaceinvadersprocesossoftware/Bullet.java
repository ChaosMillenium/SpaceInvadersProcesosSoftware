package procesos.grp7.spaceinvadersprocesossoftware;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Random;

public class Bullet {

    private PlayActivity context;
    private RelativeLayout gameLayout;
    public static final int UP = 0;
    public static final int DOWN = 1;
    private int direction; //-1 hacia arriba, 1 hacia abajo
    public static final int DURATION = 2000;
    private Point screenSize;
    private ImageView bulletView;
    private List<ImageView> gameViews, vistasMarcianos;
    private BulletCollisionDetector collisionDetector;
    private boolean fromMarciano;
    private boolean fromNave;
    private ImageView[] bordes;
    private Animator animator;
    private boolean rebote;

    public Bullet(PlayActivity context, RelativeLayout gameLayout, int direction, List<ImageView> gameViews, List<ImageView> vistasMarcianos, boolean fromMarciano, ImageView[] bordes, boolean rebote) {
        this.context = context;
        this.gameLayout = gameLayout;
        this.direction = direction;
        screenSize = new Point();
        context.getWindowManager().getDefaultDisplay().getSize(screenSize);
        this.gameViews = gameViews;
        this.vistasMarcianos = vistasMarcianos;
        this.fromMarciano = fromMarciano;
        this.fromNave = !fromMarciano;
        this.bordes = bordes;
        this.rebote = rebote;
    }

    public void generateView(float coordsX, float sizeShipX, float coordsY) {
        final ImageView bulletView = new ImageView(context);
        bulletView.setImageResource(R.drawable.bullet);
       /* RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (this.direction == Bullet.UP) {
            params.addRule(RelativeLayout.ABOVE, id);
        } else {
            params.addRule(RelativeLayout.BELOW, id);
        }*/
        bulletView.setX(coordsX + (sizeShipX / 2) - 2);
        bulletView.setY(coordsY);
        //params.setMargins(0, (int) coordsY, 0, 5);
        gameLayout.addView(bulletView);
        this.bulletView = bulletView;
        //ObjectAnimator puede dar problemas a la hora de comprobar colisiones.
        if (direction == UP) {
            Log.d("BARRERA_Y", bordes[direction].getY() + "");
            Log.d("BARRERA_Y", bulletView.getY() + "");
        }
        animator = ObjectAnimator.ofFloat(bulletView, "translationY", bulletView.getY(), bordes[direction].getY());
        animator.setDuration(DURATION);
        animator.setInterpolator(new LinearInterpolator());
        Animator.AnimatorListener listener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d("BULLET_Y_START", bulletView.getY() + "");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("BULLET_Y_END", bulletView.getY() + "");
                if (bulletView.getX() < 0 || bulletView.getX() > screenSize.x) {
                    gameLayout.removeView(bulletView);
                } else {
                    direction = (direction == UP) ? DOWN : UP;
                    collisionDetector.bounce();
                    fromMarciano = false;
                    fromNave = false;
                    if (rebote) {
                        Random r = new Random();
                        float offsetX = r.nextFloat();
                        ObjectAnimator animatorX = ObjectAnimator.ofFloat(bulletView, "translationX", bulletView.getX(), offsetX * screenSize.x);
                        ObjectAnimator animatorY = ObjectAnimator.ofFloat(bulletView, "translationY", bulletView.getY(), bordes[direction].getY());
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(animatorX, animatorY);
                        animatorSet.setInterpolator(new LinearInterpolator());
                        animatorSet.setDuration(DURATION);
                        animatorSet.addListener(this);
                        animator = animatorSet;
                        animator.start();
                    }else{
                        gameLayout.removeView(bulletView);
                    }
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
        animator.addListener(listener);
        animator.start();
        collisionDetector = new BulletCollisionDetector(this, gameViews, context, fromMarciano, vistasMarcianos);
        Thread collisionDetectorThread = new Thread(collisionDetector, "Bullet");
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

    public boolean isFromNave() {
        return fromNave;
    }
}
