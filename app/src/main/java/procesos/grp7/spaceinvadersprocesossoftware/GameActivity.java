package procesos.grp7.spaceinvadersprocesossoftware;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private ImageView spriteShip;
    private RelativeLayout gameLayout;
    private Point screenSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        spriteShip = findViewById(R.id.ship);
        gameLayout = findViewById(R.id.layout_game);
        screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
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
        try {
            Bullet bullet = new Bullet(this, gameLayout, Bullet.UP, screenSize);
            bullet.generateView();
            bullet.start();
        } catch (IllegalStateException e) {
            Log.w("ILLEGAL_STATE",e.getMessage());
        }
    }
}