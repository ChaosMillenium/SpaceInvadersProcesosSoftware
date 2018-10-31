package procesos.grp7.spaceinvadersprocesossoftware;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class VistaInvader extends Thread {
    private ArrayList<Marciano> marcianos;
    private List<ImageView> vistasMarcianos;
    private int numMarcianos;
    private int screenX;
    private int screenY;
    private GameActivity context;
    private boolean pared;
    private RelativeLayout layout;
    private List<ImageView> gameViews;
    private ImageView[] bordes;

    public VistaInvader(GameActivity context, int screenX, int screenY, RelativeLayout layout, List<ImageView> views, ImageView[] bordes) {
        this.marcianos = new ArrayList<>();
        this.vistasMarcianos = new ArrayList<>();
        this.layout = layout;
        this.context = context;
        this.screenX = screenX;
        this.screenY = screenY;
        this.pared = false;
        this.gameViews = views;
        this.bordes = bordes;
        rellenaMarcianos();
    }

    public void rellenaMarcianos() {
        this.marcianos.clear();
        this.numMarcianos = 0;
        for (int column = 0; column < 6; column++) {
            for (int row = 0; row < 4; row++) {
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
            while ((this.numMarcianos != 0) && !context.dead) {
                Thread.sleep(175);
                this.context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        movimiento();
                        dibuja();
                        if (((int) (Math.random() * 15) + 1) == 3) {
                            int marciano = (int) (Math.random() * aux);
                            if (marcianos.get(marciano).getSpriteMarciano().getVisibility() == View.VISIBLE) {
                                disparo(marciano);
                            }
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
        final Bullet bullet = new Bullet(this.context, this.layout, Bullet.DOWN, gameViews, getVistasMarcianos(), true, bordes);
        float coordX = this.marcianos.get(marciano).getX();
        float sizeX = this.marcianos.get(marciano).getLength();
        float coordsY = this.marcianos.get(marciano).getY();
        bullet.generateView(coordX, sizeX, coordsY);
    }

    public List<ImageView> getVistasMarcianos() {
        return vistasMarcianos;
    }

    public boolean respawn(){
        boolean aux = true;
        for(Marciano m : this.marcianos){
            if(m.getSpriteMarciano().getVisibility() == View.VISIBLE){
                aux = false;
            }
        }
        return aux;
    }
}
