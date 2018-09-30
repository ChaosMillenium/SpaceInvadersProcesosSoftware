package procesos.grp7.spaceinvadersprocesossoftware;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class GameActivity extends AppCompatActivity {
    ImageView spriteNave;
    Display display ;
    Point size;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        spriteNave = findViewById(R.id.nave);
        display = getWindowManager().getDefaultDisplay();
        layout = findViewById(R.id.game_layout);
        size = new Point();
        display.getSize(size);
        VistaInvader marcianitos= new VistaInvader(this, size.x, size.y, layout);
        marcianitos.start();
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