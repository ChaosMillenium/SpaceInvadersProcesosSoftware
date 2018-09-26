package procesos.grp7.spaceinvadersprocesossoftware;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener{
    ImageView sprite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        sprite = findViewById(R.id.nave);

        //Definicion de botones
        Button buttonLeft = findViewById(R.id.button_izq);
        Button buttonRight = findViewById(R.id.button_der);

        //Listeners del boton izquierdo
        buttonLeft.setOnClickListener(this);
        buttonLeft.setOnLongClickListener(this);
        buttonLeft.setOnTouchListener(this);

        //Listeners del boton derecho
        buttonRight.setOnClickListener(this);
        buttonRight.setOnLongClickListener(this);
        buttonRight.setOnTouchListener(this);
    }

    public void onClick(View view){
        switch(view.getId()) {
            case R.id.button_der:
                this.mueveDerecha(view);
                break;

            case R.id.button_izq:
                this.mueveIzquierda(view);
                break;

            default:
                break;
        }
    }

    public boolean onLongClick(View view){
        switch(view.getId()){
            case R.id.button_der:
                this.mueveDerecha(view);
                break;

            case R.id.button_izq:
                this.mueveIzquierda(view);
                break;
            default:
                break;
        }
        return true;
    }

    public boolean onTouch(View view, MotionEvent event){
        switch(view.getId()){
            case R.id.button_der:
                this.mueveDerecha(view);
                break;

            case R.id.button_izq:
                this.mueveIzquierda(view);
                break;
            default:
                break;
        }
        return true;
    }


    public void mueveIzquierda(View view) {
        float desplazamiento = sprite.getX() - 10;
        sprite.setX(desplazamiento);
    }

    public void mueveDerecha(View view) {
        float desplazamiento = sprite.getX() + 10;
        sprite.setX(desplazamiento);
    }
}