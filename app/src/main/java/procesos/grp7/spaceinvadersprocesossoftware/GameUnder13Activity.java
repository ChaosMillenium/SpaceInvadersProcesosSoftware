package procesos.grp7.spaceinvadersprocesossoftware.Screens;

import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import procesos.grp7.spaceinvadersprocesossoftware.Defensas;
import procesos.grp7.spaceinvadersprocesossoftware.Marcianos.FallingInvaders;
import procesos.grp7.spaceinvadersprocesossoftware.PlayActivity;
import procesos.grp7.spaceinvadersprocesossoftware.R;
import procesos.grp7.spaceinvadersprocesossoftware.Colisiones.ShipCollisionDetector;
import procesos.grp7.spaceinvadersprocesossoftware.VistaDefensas;


public class GameUnder13Activity extends PlayActivity implements View.OnTouchListener {
    private ImageView spriteShip;
    private RelativeLayout gameLayout;
    Display display;
    Point size;
    Button buttonLeft;
    Button buttonRight;
    private boolean pressedLeft = false;
    private boolean pressedRight = false;
    private int speedShip; //Velocidad de la nave
    private List<ImageView> gameViews = Collections.synchronizedList(new ArrayList<ImageView>(32));
    private VistaDefensas defensas;
    private static final int SPEEDSHIP_DENOM = 700; //denominador para calcular velocidad: mayor valor, mayor velocidad

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_under13);
        spriteShip = findViewById(R.id.ship);
        gameLayout = findViewById(R.id.layout_game);
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        //Definicion de botones
        buttonLeft = findViewById(R.id.button_izq);
        buttonRight = findViewById(R.id.button_der);
        //Listeners del boton izquierdo
        buttonLeft.setOnTouchListener(this);
        //Listeners del boton derecho
        buttonRight.setOnTouchListener(this);
        FallingInvaders marcianos = new FallingInvaders(this, size.x, size.y, gameLayout, gameViews);
        speedShip = size.x/SPEEDSHIP_DENOM;
        marcianos.start();
        defensas = new VistaDefensas(gameLayout, this, size.x, size.y, gameViews);
        Thread shipCollisionDetector = new Thread(new ShipCollisionDetector(this, gameViews, spriteShip));
        shipCollisionDetector.start();
    }

    public boolean onTouch(View view, MotionEvent event) {
        //Switch para ver que boton se pulsa
        switch (view.getId()) {
            case R.id.button_der:
                //switch que detecta el inicio de la pulsacion
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!pressedRight) {
                            pressedRight = true;
                            new GameUnder13Activity.MovimientoNave().execute();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        pressedRight = false;
                }
                break;
            case R.id.button_izq:
                //Switch que detecta el fin de la pulsacion
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!pressedLeft) {
                            pressedLeft = true;
                            new GameUnder13Activity.MovimientoNave().execute();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        pressedLeft = false;
                }
                break;
        }
        return true;
    }

    public void kill(final Object collider1, final ImageView collider2) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (collider1 instanceof Defensas) {
                    gameViews.remove(((Defensas) collider1).getSprite());
                    ((Defensas) collider1).getSprite().setVisibility(View.INVISIBLE);
                }
                if (collider2 == spriteShip) {
                    try {
                        if (!dead) {
                            collider2.setVisibility(View.INVISIBLE);
                            dead = true;
                            Thread.sleep(1000);
                            Intent deathIntent = new Intent(GameUnder13Activity.this, GameOverScreenUnderThirteen.class);
                            finish();
                            startActivity(deathIntent);
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    gameViews.remove(collider2);
                    collider2.setVisibility(View.INVISIBLE);
                }
            }
        });
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
                float desplazamiento = spriteShip.getX() - speedShip;
                spriteShip.setX(desplazamiento);
            }

        }

        public void mueveDerecha() {

            int height = gameLayout.getWidth() - spriteShip.getWidth();
            if (spriteShip.getX() < height) {
                spriteShip.setX(spriteShip.getX() + speedShip);
            }
        }


    }
}
