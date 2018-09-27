package procesos.grp7.spaceinvadersprocesossoftware;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private ImageView shipView;
    private RelativeLayout gameLayout;
    private ArrayList<Bullet> bullets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        shipView = findViewById(R.id.ship);
        gameLayout = findViewById(R.id.layout_game);
    }

    public void mueveIzquierda(View view) {
        float desplazamiento = shipView.getX() - 10;
        shipView.setX(desplazamiento);
    }

    public void mueveDerecha(View view) {
        float desplazamiento = shipView.getX() + 10;
        shipView.setX(desplazamiento);
    }

    public void disparar(View view){
        Bullet bullet = new Bullet(this, gameLayout,Bullet.UP);
        bullet.update();
    }
}