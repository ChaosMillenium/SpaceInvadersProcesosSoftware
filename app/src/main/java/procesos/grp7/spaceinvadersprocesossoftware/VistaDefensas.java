package procesos.grp7.spaceinvadersprocesossoftware;

import android.graphics.Point;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class VistaDefensas {
    private RelativeLayout layout;
    private PlayActivity context;
    private ArrayList<Defensas> defensas;
    private List<ImageView> vistaDefensa;
    private List<ImageView> gameViews;
    private int width;
    private int height;

    public VistaDefensas(RelativeLayout layout, PlayActivity context, int width, int height, List<ImageView> gameViews) {
        this.layout = layout;
        this.context = context;
        this.gameViews = gameViews;
        this.defensas = new ArrayList<>();
        this.vistaDefensa = new ArrayList<>();
        this.width = width;
        this.height = height;
        rellenaDefensas();
        rellenaDefensasVista();
    }

    public void rellenaDefensas() {
        int y = (height / 24) * 14;
        int x = width / 9;
        int pixelesDefensa = 115;
        Point size = new Point();
        context.getWindowManager().getDefaultDisplay().getSize(size);
        int nivel2 = size.y / 30;
        int nivel3 = nivel2 * 2;

        Defensas defensa1_1 = new Defensas(layout, context, x, y);
        Defensas defensa1_2 = new Defensas(layout, context, x, y - nivel2);
        Defensas defensa1_3 = new Defensas(layout, context, x, y - nivel3);
        Defensas defensa2_1 = new Defensas(layout, context, 3 * x, y);
        Defensas defensa2_2 = new Defensas(layout, context, 3 * x, y - nivel2);
        Defensas defensa2_3 = new Defensas(layout, context, 3 * x, y - nivel3);
        Defensas defensa3_1 = new Defensas(layout, context, width - (3 * x) - pixelesDefensa, y);
        Defensas defensa3_2 = new Defensas(layout, context, width - (3 * x) - pixelesDefensa, y - nivel2);
        Defensas defensa3_3 = new Defensas(layout, context, width - (3 * x) - pixelesDefensa, y - nivel3);
        Defensas defensa4_1 = new Defensas(layout, context, width - x - pixelesDefensa, y);
        Defensas defensa4_2 = new Defensas(layout, context, width - x - pixelesDefensa, y - nivel2);
        Defensas defensa4_3 = new Defensas(layout, context, width - x - pixelesDefensa, y - nivel3);

        this.defensas.add(defensa1_1);
        this.defensas.add(defensa1_2);
        this.defensas.add(defensa1_3);
        this.defensas.add(defensa2_1);
        this.defensas.add(defensa2_1);
        this.defensas.add(defensa2_2);
        this.defensas.add(defensa2_3);
        this.defensas.add(defensa3_1);
        this.defensas.add(defensa3_2);
        this.defensas.add(defensa3_3);
        this.defensas.add(defensa4_1);
        this.defensas.add(defensa4_2);
        this.defensas.add(defensa4_3);

        for (Defensas def : defensas) {
            Thread defensaCollisionDetector = new Thread(new DefensaCollisionDetector(context, gameViews, def));
            defensaCollisionDetector.start();
        }
    }

    public void rellenaDefensasVista() {
        for (int i = 0; i < this.defensas.size(); i++) {
            this.vistaDefensa.add(this.defensas.get(i).getSprite());
        }
    }

    public ArrayList<Defensas> getDefensas() {
        return defensas;
    }

    public List<ImageView> getVistaDefensa() {
        return vistaDefensa;
    }
}
