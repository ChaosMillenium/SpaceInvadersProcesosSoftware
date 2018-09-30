package procesos.grp7.spaceinvadersprocesossoftware;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class GameActivity extends AppCompatActivity {
    private ImageView spriteShip;
    private RelativeLayout gameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        spriteShip = findViewById(R.id.ship);
        gameLayout = findViewById(R.id.layout_game);
    }

    public void mueveIzquierda(View view) {
        float desplazamiento = spriteShip.getX() - 10;
        spriteShip.setX(desplazamiento);
    }

    public void mueveDerecha(View view) {
        float desplazamiento = spriteShip.getX() + 10;
        spriteShip.setX(desplazamiento);
    }

    public void disparar(View view) {
            Bullet bullet = new Bullet(this, gameLayout, Bullet.UP);
            float coords = spriteShip.getX();
            float coords2 = spriteShip.getY();
            bullet.generateView(coords, coords2);
    }
}