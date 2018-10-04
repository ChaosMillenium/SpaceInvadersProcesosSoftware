package procesos.grp7.spaceinvadersprocesossoftware;

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


public class GameUnder13Activity extends AppCompatActivity implements View.OnTouchListener{
    private ImageView spriteShip;
    private RelativeLayout gameLayout;
    Display display;
    Point size;
    Button buttonLeft;
    Button buttonRight;
    private boolean pressedLeft = false;
    private boolean pressedRight = false;
    private int speedShip = 2; //Velocidad de la nave

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

        FallingInvaders marcianos = new FallingInvaders(this, size.x, size.y, gameLayout);
        marcianos.start();
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
