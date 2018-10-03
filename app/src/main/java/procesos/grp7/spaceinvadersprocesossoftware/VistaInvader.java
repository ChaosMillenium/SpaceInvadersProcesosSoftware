package procesos.grp7.spaceinvadersprocesossoftware;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class VistaInvader extends Thread {
    private ArrayList<Marciano> marcianos;
    private ArrayList<View> vistasMarcianos;
    private int numMarcianos;
    private int screenX;
    private int screenY;
    private Activity context;
    private boolean pared;
    private RelativeLayout layout;
    private List<View> gameViews;

    public VistaInvader(Activity context, int screenX, int screenY, RelativeLayout layout, List<View> views) {
        this.marcianos = new ArrayList<>();
        this.vistasMarcianos = new ArrayList<>();
        this.layout = layout;
        this.context = context;
        this.screenX = screenX;
        this.screenY = screenY;
        this.pared = false;
        rellenaMarcianos();
        this.gameViews = views;
    }

    public void rellenaMarcianos() {
        this.numMarcianos = 0;
        for (int column = 0; column < 6; column++) {
            for (int row = 0; row < 5; row++) {
                Marciano nuevoMarciano = new Marciano(this.context, screenX, screenY, row, column);
                this.marcianos.add(numMarcianos, nuevoMarciano);
                nuevoMarciano.addImageView(this.layout, numMarcianos);
                vistasMarcianos.add(nuevoMarciano.getSpriteMarciano());
                numMarcianos++;
            }
        }
    }

    public void run() {
        final int aux = this.marcianos.size();
        try {
            while (true) {
                Thread.sleep(150);
                movimiento();
                dibuja();
                this.context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        if(((int) (Math.random() * 10) + 1 )== 3){
                            int marciano = (int) (Math.random() * aux);
                            disparo(marciano);
                            Log.d("Disparo", "Disparo del marcianito " + marciano);
                        }
                    }
                });
            }

        } catch (InterruptedException e) {
            System.out.print(e.getCause());
        }
    }

    public void movimiento() {
        for (int i = 0; i < this.marcianos.size(); i++) {
            if (marcianos.get(i).vivo()) {
                Log.d("MARCIANO_POS",marcianos.get(i).getX()+","+marcianos.get(i).getY());
                marcianos.get(i).actualizaPosicion();
                if (marcianos.get(i).getX() > screenX - marcianos.get(i).getLength() || marcianos.get(i).getX() < 0) {
                    pared = true;
                }
            }
        }
        if (pared) {
            for (int i = 0; i < this.marcianos.size(); i++) {
                marcianos.get(i).choquePared();
                pared = false;
            }
        }
    }

    public void dibuja() {
        for (int i = 0; i < this.marcianos.size(); i++) {
            if (marcianos.get(i).vivo()) {
                this.marcianos.get(i).dibuja();
            }
        }
    }

    public void disparo(int marciano) {
        final Bullet bullet = new Bullet(this.context, this.layout, Bullet.DOWN);
        float coordX = this.marcianos.get(marciano).getX();
        float sizeX = this.marcianos.get(marciano).getLength();
        float coordsY = this.marcianos.get(marciano).getY();
        Log.d("YDisparo", "El marciano tiene y " + coordsY);
        Log.d("YDisparo", "El marciano tiene id " + marciano);
        bullet.generateView(coordX, sizeX, coordsY, marciano);
        /*Thread collisionDetector = new Thread(new Runnable() {
            @Override
            public void run() {
                long aliveTime = 0;
                long startTime = System.currentTimeMillis();
                long actualTime;
                while (aliveTime < Bullet.DURATION) {
                    View collider = bullet.detectCollision(gameViews);
                    if (collider == null) {
                        Log.d("BULLET_COLLISION", "No collision");
                    } else {
                        Log.d("BULLET_COLLISION", collider.toString());
                        return;
                    }
                    actualTime = System.currentTimeMillis();
                    aliveTime = actualTime - startTime;
                }
            }
        });
        collisionDetector.start();*/
    }

    public ArrayList<View> getVistasMarcianos() {
        return vistasMarcianos;
    }
}
