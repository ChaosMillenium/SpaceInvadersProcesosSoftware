package procesos.grp7.spaceinvadersprocesossoftware;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener{
    ImageView sprite;
    Button buttonLeft;
    Button buttonRight;
    boolean pressedLeft = false;
    boolean pressedRight = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        sprite = findViewById(R.id.nave);

        //Definicion de botones
        buttonLeft = findViewById(R.id.button_izq);
        buttonRight = findViewById(R.id.button_der);

        //Listeners del boton izquierdo
        //buttonLeft.setOnClickListener(this);
        //buttonLeft.setOnLongClickListener(this);
        buttonLeft.setOnTouchListener(this);

        //Listeners del boton derecho
        //buttonRight.setOnClickListener(this);
        //buttonRight.setOnLongClickListener(this);
        buttonRight.setOnTouchListener(this);
    }


/*
    public boolean onTouch(View view, MotionEvent event){
        switch(view.getId()){
            case R.id.button_der:
                this.mueveDerecha();
                break;
            case R.id.button_izq:
                this.mueveIzquierda();
                break;
            default:
                break;
        }
        return true;
    }
*/
    public boolean onTouch(View view, MotionEvent event){
        switch(view.getId()){
            case R.id.button_der:
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if (!pressedRight){
                            pressedRight = true;
                            new MovimientoNave().execute();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        pressedRight = false;
                }
                break;
            case R.id.button_izq:
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if (!pressedLeft){
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

    private class MovimientoNave extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            while (pressedRight){
                mueveDerecha();
                try{
                    Thread.sleep(1);
                }catch(InterruptedException ex){ex.printStackTrace();}
            }
            while (pressedLeft){
                mueveIzquierda();
                try{
                    Thread.sleep(1);
                }catch(InterruptedException ex){ex.printStackTrace();}
            }

            return null;
        }
    }



    public void mueveIzquierda() {

        float desplazamiento = sprite.getX() - 1;
            sprite.setX(desplazamiento);


    }

    public void mueveDerecha() {

            float desplazamiento = sprite.getX() + 1;
            sprite.setX(desplazamiento);
            Log.d("Movimiento", "movio a derecha");

    }


}