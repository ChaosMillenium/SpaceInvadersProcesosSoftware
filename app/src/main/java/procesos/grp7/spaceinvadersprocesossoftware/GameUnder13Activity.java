package procesos.grp7.spaceinvadersprocesossoftware;

import android.app.Activity;
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


public class GameUnder13Activity extends PlayActivity implements View.OnTouchListener {
    private ImageView spriteShip;
    private RelativeLayout gameLayout;
    Display display;
    Point size;
    Button buttonLeft;
    Button buttonRight;
    Button buttonUp;
    Button buttonDown;
    private boolean pressedLeft = false;
    private boolean pressedRight = false;
    private boolean pressedUp = false;
    private boolean pressedDown = false;

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
        buttonUp = findViewById(R.id.button_up);
        buttonDown = findViewById(R.id.button_down);
        //Listeners de los botones
        buttonLeft.setOnTouchListener(this);
        buttonRight.setOnTouchListener(this);
        buttonUp.setOnTouchListener(this);
        buttonDown.setOnTouchListener(this);

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
            case R.id.button_up:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!pressedUp) {
                            pressedUp = true;
                            new GameUnder13Activity.MovimientoNave().execute();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        pressedUp = false;
                }
                break;
            case R.id.button_down:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!pressedDown) {
                            pressedDown = true;
                            new GameUnder13Activity.MovimientoNave().execute();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        pressedDown = false;
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
            while (pressedUp) {
                mueveArriba();
                try {
                    Thread.sleep(3);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            while (pressedDown) {
                mueveAbajo();
                try {
                    Thread.sleep(3);
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

        private void mueveAbajo() {
            int height = gameLayout.getHeight() - spriteShip.getHeight();
            if (spriteShip.getY() < height) {
                spriteShip.setY(spriteShip.getY() + speedShip);
            }
        }

        private void mueveArriba() {
            if (spriteShip.getY() > 0) {
                float desplazamiento = spriteShip.getY() - speedShip;
                spriteShip.setY(desplazamiento);
            }

        }


    }
}
