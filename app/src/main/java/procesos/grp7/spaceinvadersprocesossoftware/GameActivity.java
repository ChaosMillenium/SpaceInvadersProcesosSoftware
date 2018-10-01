package procesos.grp7.spaceinvadersprocesossoftware;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import android.widget.RelativeLayout;

public class GameActivity extends AppCompatActivity {
    private ImageView spriteShip;
    private RelativeLayout gameLayout;
    private ArrayList<View> gameViews;
    Display display ;
    Point size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        spriteShip = findViewById(R.id.ship);
        gameLayout = findViewById(R.id.layout_game);
        gameViews = new ArrayList<>();
        gameViews.add(spriteShip);
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        VistaInvader marcianitos= new VistaInvader(this, size.x, size.y, layout);
        marcianitos.start();
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
            float coordX = spriteShip.getX();
            float sizeX = spriteShip.getWidth();
            bullet.generateView(coordX, sizeX);
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