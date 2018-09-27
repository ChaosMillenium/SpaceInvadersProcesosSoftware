package procesos.grp7.spaceinvadersprocesossoftware;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity {
    ImageView spriteNave;
    ImageView spriteMarciano;
    DisplayMetrics metrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        spriteNave = findViewById(R.id.nave);
        spriteMarciano = findViewById(R.id.marcianito);
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Marciano marciano = new Marciano(spriteMarciano, metrics.widthPixels, metrics.heightPixels);
        marciano.start();
    }

    public void mueveIzquierda(View view) {
        float desplazamiento = spriteNave.getX() - 10;
        spriteNave.setX(desplazamiento);
    }

    public void mueveDerecha(View view) {
        float desplazamiento = spriteNave.getX() + 10;
        spriteNave.setX(desplazamiento);
    }
}