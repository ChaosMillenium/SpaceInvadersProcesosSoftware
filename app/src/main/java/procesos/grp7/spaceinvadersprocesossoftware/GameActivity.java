package procesos.grp7.spaceinvadersprocesossoftware;

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
    private ArrayList<View> gameViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        spriteShip = findViewById(R.id.ship);
        gameLayout = findViewById(R.id.layout_game);
        gameViews = new ArrayList<>();
        gameViews.add(spriteShip);
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
            final Bullet bullet = new Bullet(this, gameLayout, Bullet.UP);
            float coords = spriteShip.getX();
            float coords2 = spriteShip.getY();
            bullet.generateView(coords, coords2);
            Thread collisionDetector = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(bullet.isInScreen()){
                        View collider = bullet.detectCollision(gameViews);
                        if (collider==null){
                            Log.d("BULLET_COLLISION", "No collision");
                        }
                        else{
                            Log.d("BULLET_COLLISION", collider.toString());
                        }
                    }
                }
            });
            collisionDetector.start();
    }
}