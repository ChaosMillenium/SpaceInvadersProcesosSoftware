package procesos.grp7.spaceinvadersprocesossoftware;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.util.List;
import java.util.Random;

public class BulletCollisionDetector extends CollisionDetector implements Runnable {

    private Bullet bullet;
    private List<ImageView> gameViews;
    private PlayActivity activity;
    private boolean fromMarciano;
    private boolean fromNave;
    private List<ImageView> listaMarcianos;
    long tiempodecolisionanterior=0;

    public BulletCollisionDetector(Bullet bullet, List<ImageView> gameViews, PlayActivity activity, boolean fromMarciano, List<ImageView> listaMarcianos) {
        this.bullet = bullet;
        this.gameViews = gameViews;
        this.activity = activity;
        this.fromMarciano = fromMarciano;
        this.fromNave = !fromMarciano;
        this.listaMarcianos = listaMarcianos;
    }
    public void cambiarColor(){
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        for (ImageView m:this.listaMarcianos) {
            m.setColorFilter(color);
        }
    }
    public void cambiarColorAleatorio(){
        Random rnd = new Random();
        int color ;
        for (ImageView m:this.listaMarcianos) {
            color=Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            m.setColorFilter(color);
        }
    }
    public void cambiarColorComprobandoTiempo(ImageView collider){
        if (((BitmapDrawable) collider.getDrawable()).getBitmap().sameAs(((BitmapDrawable) activity.getResources().getDrawable(R.drawable.defensas)).getBitmap())){
            long tiempodecolision=System.currentTimeMillis();
            if (((tiempodecolisionanterior+100))>=tiempodecolision){
                tiempodecolisionanterior=tiempodecolision;
                cambiarColorAleatorio();
            }else{
                tiempodecolisionanterior=tiempodecolision;
                cambiarColor();
            }
        }
    }
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        tiempodecolisionanterior=startTime;
        while (true) {
            final ImageView collider = detectCollision(gameViews, bullet.getBulletView());
            if (collider != null) {
                cambiarColorComprobandoTiempo(collider);
                if (fromMarciano) {
                    if (!listaMarcianos.contains(collider)) {
                        activity.kill(bullet, collider);
                        return;
                    }
                } else {
                    activity.kill(bullet, collider);
                    return;
                }
            }
        }
    }
    public void bounce(){
        fromMarciano=false;
        fromNave=false;
    }
}
