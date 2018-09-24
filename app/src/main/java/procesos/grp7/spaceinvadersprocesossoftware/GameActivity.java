package procesos.grp7.spaceinvadersprocesossoftware;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity {
    ImageView sprite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        sprite = findViewById(R.id.nave);
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