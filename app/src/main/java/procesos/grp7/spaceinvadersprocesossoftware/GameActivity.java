package procesos.grp7.spaceinvadersprocesossoftware;

import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener {
    private ImageView spriteShip;
    private RelativeLayout gameLayout;
    private ArrayList<View> gameViews;
    Display display;
    Point size;
    Button buttonLeft;
    Button buttonRight;
    boolean pressedLeft = false;
    boolean pressedRight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        spriteShip = findViewById(R.id.ship);
        gameLayout = findViewById(R.id.layout_game);
        gameViews = new ArrayList<>();
        gameViews.add(spriteShip);
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        VistaInvader marcianitos = new VistaInvader(this, size.x, size.y, gameLayout);
        marcianitos.start();
        //Definicion de botones
        buttonLeft = findViewById(R.id.button_izq);
        buttonRight = findViewById(R.id.button_der);
        //Listeners del boton izquierdo
        buttonLeft.setOnTouchListener(this);
        //Listeners del boton derecho
        buttonRight.setOnTouchListener(this);
    }

    public boolean onTouch(View view, MotionEvent event) {
        switch (view.getId()) {
            case R.id.button_der:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!pressedRight) {
                            pressedRight = true;
                            new MovimientoNave().execute();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        pressedRight = false;
                }
                break;
            case R.id.button_izq:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!pressedLeft) {
                            pressedLeft = true;
                            new MovimientoNave().execute();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        pressedLeft = false;
                }
                break;
        }
        return true;

    }

    public void disparar(View view) {
        final Bullet bullet = new Bullet(this, gameLayout, Bullet.UP);
        float coordX = spriteShip.getX();
        float sizeX = spriteShip.getWidth();
        bullet.generateView(coordX, sizeX);
        Thread collisionDetector = new Thread(new Runnable() {
            @Override
            public void run() {
                while (bullet.isInScreen()) {
                    View collider = bullet.detectCollision(gameViews);
                    if (collider == null) {
                        Log.d("BULLET_COLLISION", "No collision");
                    } else {
                        Log.d("BULLET_COLLISION", collider.toString());
                    }
                }
            }
        });
        collisionDetector.start();
    }

    private class MovimientoNave extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            while (pressedRight) {
                mueveDerecha();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            while (pressedLeft) {
                mueveIzquierda();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            return null;
        }

        public void mueveIzquierda() {
            if (spriteShip.getX() > 0) {
                float desplazamiento = spriteShip.getX() - 1;
                spriteShip.setX(desplazamiento);
            }

        }


        public void mueveDerecha() {
            RelativeLayout layout = findViewById(R.id.layout_game);
            int height = layout.getWidth() - spriteShip.getWidth();
            if (spriteShip.getX() < height) {
                spriteShip.setX(spriteShip.getX() + 1);
            }
        }


    }
}