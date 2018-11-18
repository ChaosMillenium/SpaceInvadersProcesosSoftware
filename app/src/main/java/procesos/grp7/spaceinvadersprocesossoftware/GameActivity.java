package procesos.grp7.spaceinvadersprocesossoftware;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameActivity extends PlayActivity implements View.OnTouchListener {
    private ImageView spriteShip;
    private RelativeLayout gameLayout;
    private CopyOnWriteArrayList<ImageView> gameViews;
    private List<ImageView> vistasMarcianos;
    private int puntos;
    TextView marcadorPuntos;
    Display display;
    Point size;
    Button buttonLeft;
    Button buttonRight;
    Button buttonUp;
    Button buttonDown;
    private boolean rebotes;
    private boolean pressedLeft = false;
    private boolean pressedRight = false;
    private boolean pressedUp = false;
    private boolean pressedDown = false;
    VistaInvader marcianitos;
    VistaDefensas defensas;
    private VistaMarcianoEspecial marcianoEspecial;
    private int speedShip;
    private ImageView[] bordes;
    private static final int SPEEDSHIP_DENOM = 500; //denominador para calcular velocidad: mayor valor, menor velocidad


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        String message = intent.getStringExtra("EXTRA_MESSAGE");
        puntos = Integer.parseInt(message);
        String mensajeReb = intent.getStringExtra("REBOTE");
        rebotes= (mensajeReb.equals("SI"));
        System.out.println(rebotes);
        String puntosString = Integer.toString(puntos);
        marcadorPuntos = findViewById(R.id.Puntos);
        marcadorPuntos.setText(puntosString);
        spriteShip = findViewById(R.id.ship);
        gameLayout = findViewById(R.id.layout_game);
        bordes = new ImageView[]{findViewById(R.id.border_up), findViewById(R.id.border_down)};
        bordes[1].setY(1);
        gameViews = new CopyOnWriteArrayList<>();
        marcadorPuntos = findViewById(R.id.Puntos);
        gameViews = new CopyOnWriteArrayList<>();
        gameViews.add(spriteShip);
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        marcianitos = new VistaInvader(this, size.x, size.y, gameLayout, gameViews, bordes,rebotes);
        this.vistasMarcianos = marcianitos.getVistasMarcianos();
        gameViews.addAll(marcianitos.getVistasMarcianos());
        marcianoEspecial = new VistaMarcianoEspecial(this, size.x, size.y, gameLayout, gameViews, this.vistasMarcianos, bordes,rebotes);
        gameViews.addAll(marcianoEspecial.getVistasMarciano());
        marcianitos.start();
        marcianoEspecial.start();
        defensas = new VistaDefensas(gameLayout, this, size.x, size.y, gameViews);
        gameViews.addAll(defensas.getVistaDefensa());
        speedShip = size.x / SPEEDSHIP_DENOM;
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
        final Button shoot = findViewById(R.id.button_shoot);
        shoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disparar();
                shoot.setEnabled(false);
                shoot.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        shoot.setEnabled(true);
                    }
                }, 250);
            }
        });
        dead = false;
        Thread shipCollisionDetector = new Thread(new ShipCollisionDetector(this, gameViews, spriteShip));
        shipCollisionDetector.start();
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
            case R.id.button_up:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!pressedUp) {
                            pressedUp = true;
                            new MovimientoNave().execute();
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
                            new MovimientoNave().execute();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        pressedDown = false;
                }
                break;
        }
        return true;

    }

    public void disparar() {
        if (!dead) {
            final Bullet bullet = new Bullet(this, gameLayout, Bullet.UP, gameViews, vistasMarcianos, false, bordes,rebotes);
            float coordX = spriteShip.getX();
            float sizeX = spriteShip.getWidth();
            float coordY = spriteShip.getY() - 25;
            bullet.generateView(coordX, sizeX, coordY);
        }
    }

    public void kill(final Object collider1, final ImageView collider2) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (collider2 == spriteShip) {
                    if (!dead) {
                        if (collider1 instanceof Bullet)
                            if (((Bullet) collider1).isFromNave()) return;
                        collider2.setVisibility(View.INVISIBLE);
                        dead = true;
                        Intent deathIntent = new Intent(GameActivity.this, GameOverScreen.class);
                        deathIntent.putExtra("EXTRA_POINTS", Integer.toString(puntos));
                        finish();
                        startActivityForResult(deathIntent, 1);
                    }
                } else {
                    gameViews.remove(collider2);
                    collider2.setVisibility(View.INVISIBLE);
                    if (((BitmapDrawable) collider2.getDrawable()).getBitmap().sameAs(((BitmapDrawable) getResources().getDrawable(R.drawable.spritemarciano)).getBitmap())) {
                        puntos += 100;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String puntosString = Integer.toString(puntos);
                                marcadorPuntos.setText(puntosString);
                            }
                        });
                    }
                    if (marcianitos.respawn()) {
                        Intent intent = new Intent(GameActivity.this, GameActivity.class);
                        String extra = marcadorPuntos.getText().toString();
                        intent.putExtra("EXTRA_MESSAGE", extra);
                        startActivityForResult(intent, 1);
                    }
                }
                if (collider1 instanceof Bullet) {
                    ((Bullet) collider1).delete();
                }
                if (collider1 instanceof Defensas) {
                    if (!gameViews.remove(((Defensas) collider1).getSprite())) {
                        // throw new RuntimeException("No se ha eliminado la barrera");
                        ((Defensas) collider1).getSprite().setVisibility(View.INVISIBLE);
                    }
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
                    Thread.sleep(3);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            while (pressedLeft) {
                mueveIzquierda();
                try {
                    Thread.sleep(3);
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

        private void mueveIzquierda() {
            if (spriteShip.getX() > 0) {
                float desplazamiento = spriteShip.getX() - speedShip;
                spriteShip.setX(desplazamiento);
            }

        }


        private void mueveDerecha() {
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