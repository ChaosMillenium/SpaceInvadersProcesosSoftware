package procesos.grp7.spaceinvadersprocesossoftware;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MarcianoUnder13 {
    public ImageView viewMarciano;
    private int width;
    private int height;
    private Activity context;
    private RelativeLayout layout;

    //Velocidad de las fases del movimiento de marcianos
    private static final int DURATION_PHASE1 = 1000;
    private static final int DURATION_PHASE2 = 400;
    private static final int DURATION_PHASE3 = 3500;

    private Point screenSize;
    private int screenHeight;
    private int screenWidth;
    private float xPosition, yPosition;
    private List<ImageView> gameViews;

    public MarcianoUnder13(Activity context, RelativeLayout layout, int screenX, int screenY, List<ImageView> gameViews) {
        this.width = screenX / 20;
        this.height = screenY / 20;
        this.context = context;
        this.layout = layout;
        screenSize = new Point();
        context.getWindowManager().getDefaultDisplay().getSize(screenSize);
        this.screenHeight = screenY;
        this.screenWidth = screenX;
        this.gameViews = gameViews;
    }

    public void generateMarciano(float X) {

        //Generacion de ImageView de marciano
        final ImageView marciano = new ImageView(context);
        marciano.setImageResource(R.drawable.spritemarciano);
        marciano.setX(X);
        marciano.setY(0);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.height = this.height;
        params.width = this.width;
        params.setMargins(0, 0, 0, 0);
        viewMarciano = marciano;
        gameViews.add(marciano);
        layout.addView(marciano, params);
        //Animacion de caida

        //Fase 1
        int alturaRandom = (int) (Math.random() * (screenHeight / 4)) + 1;
        final ObjectAnimator martianAnimator1 = ObjectAnimator.ofFloat(marciano, "translationY", 0f, (float) alturaRandom);
        martianAnimator1.setDuration(DURATION_PHASE1);
        martianAnimator1.setInterpolator(new LinearInterpolator());

        //Fase 2

        float startSides = marciano.getX();
        float finalSides = lateralLogic(startSides);

        final ObjectAnimator martianAnimator2 = ObjectAnimator.ofFloat(marciano, "translationX", startSides, finalSides);
        martianAnimator1.setDuration(DURATION_PHASE2);
        martianAnimator1.setInterpolator(new LinearInterpolator());

        //Fase 3
        final ObjectAnimator martianAnimator3 = ObjectAnimator.ofFloat(marciano, "translationY", alturaRandom, (float) screenHeight);
        martianAnimator3.setDuration(DURATION_PHASE3);
        martianAnimator3.setInterpolator(new LinearInterpolator());

        List<Animator> listPhases = new ArrayList<>();
        listPhases.add(martianAnimator1);
        listPhases.add(martianAnimator2);
        listPhases.add(martianAnimator3);

        AnimatorSet animation = new AnimatorSet();
        animation.playSequentially(listPhases);
        animation.start();

        //Listeners que recogen la ubicacion actual de la imagen

        martianAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                xPosition = marciano.getX();
                yPosition = marciano.getY();
            }

        });

        martianAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                xPosition = marciano.getX();
                yPosition = marciano.getY();
            }
        });

        martianAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                xPosition = marciano.getX();
                yPosition = marciano.getY();
            }
        });

        martianAnimator3.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //Nada
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                gameViews.remove(marciano);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                //Nada
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                //Nada
            }
        });
    }

    public float lateralLogic(float init) {
        float fin;
        int desp = 150;
        if (init >= 0 && init < screenWidth / 4) {
            fin = init + new Random().nextInt(desp);
            return fin;
        } else if (init >= screenWidth / 4 && init < (screenWidth / 2 + screenWidth / 4)) {
            int randomDir = new Random().nextInt(1); // 0 izquierda; 1 derecha
            if (randomDir == 0) {
                fin = init - new Random().nextInt(desp);
                return fin;
            } else {
                fin = init + new Random().nextInt(desp);
                return fin;
            }
        } else {
            fin = init - new Random().nextInt(desp);
            return fin;
        }
    }

    public int getWidth() {
        return width;
    }

}