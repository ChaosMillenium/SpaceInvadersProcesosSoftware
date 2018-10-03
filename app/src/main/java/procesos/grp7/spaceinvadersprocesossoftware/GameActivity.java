package procesos.grp7.spaceinvadersprocesossoftware;

import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener {
    private ImageView spriteShip;
    private RelativeLayout gameLayout;
    private CopyOnWriteArrayList<View> gameViews;
    private int puntos = 0;
    TextView marcadorPuntos;
    Display display;
    Point size;
    Button buttonLeft;
    Button buttonRight;
    private boolean pressedLeft = false;
    private boolean pressedRight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        spriteShip = findViewById(R.id.ship);
        gameLayout = findViewById(R.id.layout_game);
        marcadorPuntos = findViewById(R.id.Puntos);
        gameViews = new CopyOnWriteArrayList<>();
        gameViews.add(spriteShip);
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        VistaInvader marcianitos = new VistaInvader(this, size.x, size.y, gameLayout, gameViews);
        gameViews.addAll(marcianitos.getVistasMarcianos());
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
        float coordY = spriteShip.getY();
        bullet.generateView(coordX, sizeX, coordY, R.id.ship);
        Thread collisionDetector = new Thread(new Runnable() {
            @Override
            public void run() {
                long aliveTime = 0;
                long startTime = System.currentTimeMillis();
                long actualTime;
                while (aliveTime < Bullet.DURATION) {
                    final View collider = bullet.detectCollision(gameViews);
                    if (collider == null) {
                        Log.d("BULLET_COLLISION", "No collision");
                    } else {
                        Log.d("BULLET_COLLISION", collider.toString());
                        puntos += 100;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String puntosString = Integer.toString(puntos);
                                marcadorPuntos.setText(puntosString);
                            }
                        });
                        bullet.delete();
                        gameViews.remove(collider);
                        final ImageView vistaMarciano = (ImageView) collider;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                 vistaMarciano.setVisibility(View.INVISIBLE);
                            }
                        });
                        return; //???
                    }
                    actualTime = System.currentTimeMillis();
                    aliveTime = actualTime-startTime;
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
            int height = gameLayout.getWidth() - spriteShip.getWidth();
            if (spriteShip.getX() < height) {
                spriteShip.setX(spriteShip.getX() + 1);
            }
        }


    }
}