package procesos.grp7.spaceinvadersprocesossoftware;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class VistaMarcianoEspecial extends Thread{
    private MarcianoEspecial marciano;
    private List<ImageView> vistasMarciano;
    private int screenX;
    private int screenY;
    private GameActivity context;
    private RelativeLayout layout;
    private List<ImageView> gameViews;
    private Boolean temp;
    private Cronometro c;
    private List<ImageView> aliados;

    public VistaMarcianoEspecial(GameActivity context, int screenX, int screenY, RelativeLayout layout, List<ImageView> views, List<ImageView> aliados) {
        this.layout = layout;
        this.context = context;
        this.screenX = screenX;
        this.screenY = screenY;
        this.gameViews = views;
        this.temp = true;
        this.vistasMarciano = new ArrayList<>();
        this.c = new Cronometro(this);
        this.marciano = new MarcianoEspecial(this.context, screenX, screenY);
        this.marciano.addImageView(this.layout, 1);
        this.vistasMarciano.add(this.marciano.getSpriteMarciano());
        this.aliados = aliados;
        this.aliados.addAll(this.vistasMarciano);
    }

    public void disparo() {
        final Bullet bullet = new Bullet(this.context, this.layout, Bullet.DOWN, gameViews, aliados,true,context.bordes);
        float coordX = this.marciano.getX();
        float sizeX = this.marciano.getLength();
        float coordsY = this.marciano.getY();
        bullet.generateView(coordX, sizeX, coordsY);
        Thread collisionDetector = new Thread(new BulletCollisionDetector(bullet, gameViews, context, true, this.aliados));
        collisionDetector.start();
    }

    public void run() {
        try {
            this.c.start();
            while (true) {
                Thread.sleep(75);
                if(this.temp) {
                    this.context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(marciano.getX() == 0){
                                marciano.getSpriteMarciano().setVisibility(View.VISIBLE);
                            }
                            marciano.actualizaPosicion();
                            marciano.dibuja();
                            if (((int) (Math.random() * 8) + 1) == 3) {
                                if (marciano.getSpriteMarciano().getVisibility() == View.VISIBLE) {
                                    disparo();
                                }
                            }
                            if (marciano.getX() > screenX || !marciano.vivo()) {
                                temp = false;
                                marciano = null;
                                respawn();
                                marciano.getSpriteMarciano().setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        } catch (InterruptedException e) {
            System.out.print(e.getCause());
        }
    }

    public List<ImageView> getVistasMarciano() {
        return vistasMarciano;
    }

    public void respawn(){
        marciano = new MarcianoEspecial(this.context, this.screenX, this.screenY);
        marciano.addImageView(layout, 1);
        vistasMarciano.set(0, marciano.getSpriteMarciano());
        gameViews.addAll(vistasMarciano);
        aliados.addAll(vistasMarciano);
    }

    public void setTemp(Boolean b){
        this.temp = b;
    }
}
