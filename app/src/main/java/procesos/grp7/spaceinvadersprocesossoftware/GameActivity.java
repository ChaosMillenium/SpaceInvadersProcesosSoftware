package procesos.grp7.spaceinvadersprocesossoftware;

import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;
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
    private boolean pressedLeft = false;
    private boolean pressedRight = false;
    VistaInvader marcianitos;
    VistaDefensas defensas;
    private int speedShip;
    private static final int SPEEDSHIP_DENOM = 500; //denominador para calcular velocidad: mayor valor, menor velocidad


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        String message = intent.getStringExtra("EXTRA_MESSAGE");
        puntos = Integer.parseInt(message);
        String puntosString = Integer.toString(puntos);
        marcadorPuntos = findViewById(R.id.Puntos);
        marcadorPuntos.setText(puntosString);


        spriteShip = findViewById(R.id.ship);
        gameLayout = findViewById(R.id.layout_game);
        gameViews = new CopyOnWriteArrayList<>();
        marcadorPuntos = findViewById(R.id.Puntos);
        gameViews = new CopyOnWriteArrayList<>();
        gameViews.add(spriteShip);
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        marcianitos = new VistaInvader(this, size.x, size.y, gameLayout, gameViews);
        this.vistasMarcianos = marcianitos.getVistasMarcianos();
        gameViews.addAll(marcianitos.getVistasMarcianos());
        marcianitos.start();
        defensas = new VistaDefensas(gameLayout, this, size.x, size.y, gameViews);
        gameViews.addAll(defensas.getVistaDefensa());
        speedShip = size.x/SPEEDSHIP_DENOM;
        //Definicion de botones
        buttonLeft = findViewById(R.id.button_izq);
        buttonRight = findViewById(R.id.button_der);
        //Listeners del boton izquierdo
        buttonLeft.setOnTouchListener(this);
        //Listeners del boton derecho
        buttonRight.setOnTouchListener(this);
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
        }
        return true;

    }

    public void disparar(View view) {
        if (!dead) {
            final Bullet bullet = new Bullet(this, gameLayout, Bullet.UP);
            float coordX = spriteShip.getX();
            float sizeX = spriteShip.getWidth();
            float coordY = spriteShip.getY();
            bullet.generateView(coordX, sizeX, coordY, R.id.ship);
            BulletCollisionDetector collisionDetector = new BulletCollisionDetector(bullet, gameViews, this, false, vistasMarcianos);
            Thread collisionDetectorThread = new Thread(collisionDetector);
            collisionDetectorThread.start();
        }
    }

    public void kill(final Object collider1, final ImageView collider2) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (collider1 instanceof Bullet) {
                    ((Bullet) collider1).delete();
                }
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
                            Intent deathIntent = new Intent(GameActivity.this, GameOverScreen.class);
                            finish();
                            startActivity(deathIntent);
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                } else{
                    gameViews.remove(collider2);
                    collider2.setVisibility(View.INVISIBLE);
                    puntos+=100;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String puntosString = Integer.toString(puntos);
                            marcadorPuntos.setText(puntosString);
                        }
                    });

                    if(marcianitos.respawn()){
                        Intent intent = new Intent(GameActivity.this, GameActivity.class);
                        String extra = marcadorPuntos.getText().toString();
                        intent.putExtra("EXTRA_MESSAGE", extra);
                        startActivityForResult(intent, 1);
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
    }
}