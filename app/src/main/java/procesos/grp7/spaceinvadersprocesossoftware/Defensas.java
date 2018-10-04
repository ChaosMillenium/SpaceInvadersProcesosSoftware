package procesos.grp7.spaceinvadersprocesossoftware;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Defensas {
    private ImageView defensas;
    private float medidas;
    private int vidas;

    public Defensas(RelativeLayout layout, Context context, int x, int y){
        this.defensas = new ImageView(context);
        defensas.setImageResource(R.drawable.defensas);
        defensas.setVisibility(View.VISIBLE);
        defensas.setX(x);
        defensas.setY(y);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(115,26);
        defensas.setLayoutParams(params);
        layout.addView(defensas);
        this.vidas = 3;
    }

    public ImageView getDefensas(){
        return defensas;
    }

    public ImageView getSprite(){
        return this.defensas;
    }
}
